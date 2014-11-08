package com.conserata.beer.security;

import com.conserata.beer.model.UserSession;
import com.conserata.beer.repository.UserSessionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 */
@Service
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    // for token generation purposes
    private SecureRandom random = new SecureRandom();

    // plug in repository for session persistence
    @Autowired
    private UserSessionRepository userSessionRepository;

    // Ok, username and password were fine...
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        // generate a 32-char random token
        String token = new BigInteger(130, random).toString(32);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode tokenElement = mapper.createObjectNode().put("token", token);
        // create a user session for the database
        UserSession sessionData = new UserSession();
        // usersession is valid
        sessionData.setExpired(false);
        // set the token to the database
        sessionData.setSessionToken(token);
        // simple approach: just map it to a username (don't do that in production, use id)
        sessionData.setUsername(authentication.getName());
        // persist...
        userSessionRepository.save(sessionData);

        // let's check what sessions I already have and just output them to the console
        // (( useless bullshit :-) ))
        List<UserSession> userSessionList = (List<UserSession>)userSessionRepository.findAll();
        for (UserSession userSession : userSessionList) {
            System.out.println(userSession.toString());
        }
        // get back the token for the response
        // optionally we could also use a DTO object and let spring mvc do the conversion
        // here we just use a simple string
        PrintWriter out = response.getWriter();
        out.print(token);
        out.flush();
        out.close();
        System.out.println("onAuthenticationSuccess");
        // clean up a bit
        clearAuthenticationAttributes(request);

    }

}

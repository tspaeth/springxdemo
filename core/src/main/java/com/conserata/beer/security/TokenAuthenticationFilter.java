package com.conserata.beer.security;

import com.conserata.beer.model.UserSession;
import com.conserata.beer.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 *
 * This is the base implementation for filtering the headers and look for a "X-Rest-Authentication" Header
 * (custom header)
 * Whenever it exists, the request is validated against the current token-object-database table to lookup a match
 */
public class TokenAuthenticationFilter extends GenericFilterBean {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserSessionRepository userSessionRepository;

    static class RequestTokenAccess extends HttpServletRequestWrapper {
        public RequestTokenAccess(HttpServletRequest request) {
            super(request);
        }
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("TokenAuthFilter filtering");
        HttpServletRequest request = (HttpServletRequest) req;
        // header is set?
        if (request.getHeader("X-Rest-Authentication") != null) {
            // get the token value
            String value = request.getHeader("X-Rest-Authentication");
            System.out.println("Looking for usersession " + value + " in usersession database");
            // look for the value in the user session table
            UserSession currentSession = userSessionRepository.findOneUserSessionBySessionToken(value);
            // anything found?
            if (currentSession != null) {
                System.out.println("found session in session database for user" + currentSession.getUsername());
                // get the user "session" object by that now
                UserDetails userDetails = userDetailsService.loadUserByUsername(value);

                System.out.println("Rest-Header found");
                // inject the user details to the security context
                SecurityContext contextBeforeChainExecution = createSecurityContext(userDetails);
                SecurityContextHolder.setContext(contextBeforeChainExecution);
                // check if it worked :-)
                if (contextBeforeChainExecution.getAuthentication() != null && contextBeforeChainExecution.getAuthentication().isAuthenticated()) {

                    String userName = (String) contextBeforeChainExecution.getAuthentication().getPrincipal();
                    System.out.println("username: " + userName);
                }
                // proceed the filter stack...
                chain.doFilter(request, response);
                return;
            }

        }
        // we could also do some things on particular URLs - e.g. removing the session information of the Spring Key
        if (request.getRequestURI().contains("/login")) {
            System.out.println("login page");
            request.getSession().removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        }
        // proceed...
        chain.doFilter(request, response);

    }

    /**
     * Check if we already have a user context and user details are found.
     * Then prepare some information for the context
     * @param userDetails
     * @return
     */
    private SecurityContext createSecurityContext(UserDetails userDetails) {
        if (userDetails != null) {
            SecurityContextImpl securityContext = new SecurityContextImpl();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            securityContext.setAuthentication(authentication);
            return securityContext;
        }
        return SecurityContextHolder.createEmptyContext();
    }


}

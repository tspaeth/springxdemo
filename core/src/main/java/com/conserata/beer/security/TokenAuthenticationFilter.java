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
        if (request.getHeader("X-Rest-Authentication") != null) {
            String value = request.getHeader("X-Rest-Authentication");
            System.out.println("Looking for usersession " + value + " in usersession database");
            UserSession currentSession = userSessionRepository.findOneUserSessionBySessionToken(value);
            if (currentSession != null) {
                System.out.println("found session in session database for user" + currentSession.getUsername());

                UserDetails userDetails = userDetailsService.loadUserByUsername(value);

                System.out.println("Rest-Header found");
                SecurityContext contextBeforeChainExecution = createSecurityContext(userDetails);
                SecurityContextHolder.setContext(contextBeforeChainExecution);
                if (contextBeforeChainExecution.getAuthentication() != null && contextBeforeChainExecution.getAuthentication().isAuthenticated()) {
                    String userName = (String) contextBeforeChainExecution.getAuthentication().getPrincipal();
                    System.out.println("username: " + userName);
                }
                chain.doFilter(request, response);
                return;
            }

        }
        if (request.getRequestURI().contains("/login")) {
            System.out.println("login page");
            request.getSession().removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        }
        chain.doFilter(request, response);

    }

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

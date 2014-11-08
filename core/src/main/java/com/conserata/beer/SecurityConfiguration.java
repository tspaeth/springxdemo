package com.conserata.beer;

import com.conserata.beer.model.UserSession;
import com.conserata.beer.repository.UserSessionRepository;
import com.conserata.beer.security.MyAuthenticationSuccessHandler;
import com.conserata.beer.security.RestBasedAuthenticationEntryPoint;
import com.conserata.beer.security.TokenAuthenticationFilter;
import com.conserata.beer.security.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

/**
 *
 * (c) conserata IT-Consulting
 * @author tspaeth
 *
 * This class configures the Spring Security/REST-Authentication parameters
 *
 * - Static usernames demo and admin (standard C&P stuff :-) ). Contains roles, but they're not really used.
 * -
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String REALM = "Secured Environment";

    @Bean
    public BasicAuthenticationEntryPoint basicAuthenticationEntryPoint() {
        System.out.println("Basic AUTH Entry");
        RestBasedAuthenticationEntryPoint restAuthenticationEntryPoint = new RestBasedAuthenticationEntryPoint();
        restAuthenticationEntryPoint.setRealmName(REALM);
        return restAuthenticationEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("demo")
                .password("demo")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN", "USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // OK... so let the fun begin
        // How do we authenticate though?

        // Ah, ok I want some class to handle the authentication failures? Right? Here we go...
        MyAuthenticationFailureHandler failureHandler = new MyAuthenticationFailureHandler();

        http
//                .addFilterAfter(tokenAuthentication(),UsernamePasswordAuthenticationFilter.class)
                // ok, before the AbstractPReAuthenticateProcessingFilter, I'd like to have a filter that checks
                // for the existence of the Header Token (X-REST-AUTHENTICATION in the demo)
                .addFilterBefore(tokenAuthentication(), AbstractPreAuthenticatedProcessingFilter.class)
                // if you want to have some Basic Authentication stuff... not implemented though, but just for
                // further configuration purposes left in
                .exceptionHandling().authenticationEntryPoint(basicAuthenticationEntryPoint())
                .and()
                // Session Creation? ouh.. no! Let's get stateless a bit. Or kind of... doesn't matter
                .sessionManagement().enableSessionUrlRewriting(false).sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // So where does the XHR Call from the client go to? We use /auth here and that is why we allow everybody
                // to use it without authentication
                .authorizeRequests().antMatchers("/auth").permitAll()
                .and()
                // For the preflight requests of CORS, I'd like to allow them all. Otherwise, the client side will
                // get really messy about that :-)
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .and()
                 // everywhere else - please only come in if you have a valid token!
                .authorizeRequests().antMatchers("/**").authenticated()
                .and()
                // And tada... here is the BASIC Auth implementation in case somebody might wanna use that.
                .httpBasic().authenticationEntryPoint(basicAuthenticationEntryPoint())
                // CSRF disabled in demo. But for sure - for security reasons you should enable it in production
                .and().csrf().disable()
                // as we are just using form authentication handling for processing the authentication itself
                // we're just killing the redirtion and target urls to introduce custom handlers for doing the stuff
                .formLogin().successHandler(successHandler()).
                // TODO: Remove the processing url and just give back the Authorization state (HTTP 401 or 200)
                loginProcessingUrl("/auth").failureHandler(failureHandler);
    }

    @Bean
    public MyAuthenticationSuccessHandler successHandler() {
        return new MyAuthenticationSuccessHandler();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthentication() {
        return new TokenAuthenticationFilter();
    }

    /**
     * In case the credentials where not correct, send back the corresponding HTTP state to the client
     */
    private static class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

        protected static final String STATUS_MESSAGE_AUTHENTICATION_FAILED = "Bad credentials";

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, STATUS_MESSAGE_AUTHENTICATION_FAILED);
        }


    }

}

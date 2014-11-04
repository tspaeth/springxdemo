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


        MyAuthenticationFailureHandler failureHandler = new MyAuthenticationFailureHandler();

        http
//                .addFilterAfter(tokenAuthentication(),UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenAuthentication(), AbstractPreAuthenticatedProcessingFilter.class)
                .exceptionHandling().authenticationEntryPoint(basicAuthenticationEntryPoint())
                .and()
                .sessionManagement().enableSessionUrlRewriting(false).sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/auth").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/**").authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(basicAuthenticationEntryPoint())
                .and().csrf().disable()
                .formLogin().successHandler(successHandler()).
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


    private static class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

        protected static final String STATUS_MESSAGE_AUTHENTICATION_FAILED = "Bad credentials";

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, STATUS_MESSAGE_AUTHENTICATION_FAILED);
        }


    }

}

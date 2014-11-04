package com.conserata.beer;

import com.conserata.beer.model.Beer;
import com.conserata.beer.model.Company;
import com.conserata.beer.model.Rating;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.annotation.WebServlet;

/**
 * Hello world!
 *
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class App implements CommandLineRunner
{
    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "N/A", AuthorityUtils
                        .commaSeparatedStringToAuthorityList("ROLE_USER")));
        try {
//            System.out.println(this.service.secure());
        }
        finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Configuration
    static class RepositoryConfig extends RepositoryRestMvcConfiguration {
        @Override
        protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config.exposeIdsFor(Beer.class, Company.class, Rating.class);
        }
    }


}

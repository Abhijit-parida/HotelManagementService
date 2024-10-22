package com.hsm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfiguration {

    private JWTFilter jwtFilter;

    public SecurityConfiguration(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        //h(cd)2
        http.csrf().disable().cors().disable();

        //JWTFilter Class
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);

        //haap
        http.authorizeHttpRequests().anyRequest().permitAll();

        //return build
        return http.build();
    }
}

package com.dmh.msgateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .and()
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/users/registration", "/users/login").permitAll()
                .and()
                .authorizeExchange()
                .anyExchange()
                .authenticated()
                .and()
                .oauth2Login()
                .and().oauth2ResourceServer(cfg -> cfg.jwt())
                .csrf().disable()
        ;

        return http.build();
    }

}
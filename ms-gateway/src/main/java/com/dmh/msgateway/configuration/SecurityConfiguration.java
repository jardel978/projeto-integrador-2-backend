package com.dmh.msgateway.configuration;

import com.dmh.msgateway.exceptions.CustomAccessDeniedHandler;
import com.dmh.msgateway.exceptions.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET,"/actuator/**").permitAll()
                .and()
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/ms_users/users/users/registration", "/ms_users/users/users/login").permitAll()
                .and()
                .authorizeExchange()
                .anyExchange()
                .authenticated()
                .and()
                .oauth2Login()
                .and().oauth2ResourceServer(cfg -> cfg.jwt())
//                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
//                .and()
//                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                .and()
                .csrf().disable()
        ;

        return http.build();
    }

}

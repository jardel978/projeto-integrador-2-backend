package com.dmh.msusers.configuration.oauth;

import com.dmh.msusers.exceptions.CustomAccessDeniedHandler;
import com.dmh.msusers.exceptions.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/actuator/**", "/v3/api-docs/**",
                        "/swagger-ui/**", "/swagger-ui.html", "/webjars/swagger-ui/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/users/registration", "/users/login").permitAll();
        http.authorizeRequests().anyRequest().authenticated();  // todas as requisições devem ser autenticadas
        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(new KeyCloakJwtAuthenticationConverter());
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().and().csrf().disable();
    }

    //    @Bean
    //    public JwtDecoder jwtDecoder() {
    //        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8080/realms/umReino/protocol/openid-connect/certs").build();
    //    }

}

package com.dmh.msusers.configuration;

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "dmh.keycloak")
public class KeycloakClientConfig {

    @Value("${dmh.keycloak.server-url}")
    private String serverURL;
    @Value("${dmh.keycloak.realm}")
    private String realm;
    @Value("${dmh.keycloak.client-id}")
    private String clientId;
    @Value("${dmh.keycloak.client-secret}")
    private String clientSecret;

    @Bean
    public Keycloak getInstance() {

//        Keycloak keycloak = KeycloakBuilder.builder() //
//                .serverUrl(serverURL) //
//                .realm(realm) //
//                .grantType(OAuth2Constants.PASSWORD) //
//                .clientId(clientId) //
//                .clientSecret(clientSecret) //
//                .username("admin") //
//                .password("admin") //
//                .build();

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverURL)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();


        log.info("Token" + " " + keycloak.tokenManager().getAccessToken().getToken());
        log.info("serverURL" + " " + serverURL);
        log.info("clientSecret" + " " + clientSecret);
        log.info("keycloak" + keycloak.toString());

        return keycloak;
    }
}

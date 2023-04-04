package com.dmh.msusers.configuration;

import lombok.extern.slf4j.Slf4j;
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
//    @Value("${dmh.keycloak.realm}")
//    private String realm;
//    @Value("${dmh.keycloak.client-id}")
//    private String clientId;
//    @Value("${dmh.keycloak.client-secret}")
//    private String clientSecret;
    @Value("${dmh.keycloak.username}")
    private String keycloakUsername;
    @Value("${dmh.keycloak.password}")
    private String keycloakPassword;

    @Bean
    public Keycloak getInstance() {
        log.info("server-url: " + serverURL);
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverURL)
                .realm("master")
                .grantType(OAuth2Constants.PASSWORD)
                .clientId("admin-cli")
                .username(keycloakUsername)
                .password(keycloakPassword)
                .build();

//        Keycloak keycloak = KeycloakBuilder.builder()
//                .serverUrl("http://localhost:8080/")
//                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
//                .realm(realm)
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .scope(OAuth2Constants.SCOPE_OPENID)
//                .resteasyClient(
//                        new ResteasyClientBuilder()
//                                .connectionPoolSize(10).build()
//                ).build();

//        keycloak.realm(realm).clients().findAll().forEach(clientRepresentation -> {
//            log.info("keycloak (realm:" + " " + realm + " " + ")" + " " + clientRepresentation.getClientId());
//        });

        return keycloak;
    }
}

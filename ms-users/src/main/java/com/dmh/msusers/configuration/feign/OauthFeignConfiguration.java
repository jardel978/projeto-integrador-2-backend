package com.dmh.msusers.configuration.feign;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
@Slf4j
public class OauthFeignConfiguration {

    private static final String KEYCLOAK_REGISTRATION_ID = "oauth2-client-credentials";

    @Autowired
    private final OAuth2AuthorizedClientService clientService;

    @Autowired
    private final ClientRegistrationRepository registrationRepository;

    @Autowired
    public OauthFeignConfiguration(OAuth2AuthorizedClientService clientService, ClientRegistrationRepository registrationRepository) {
        this.clientService = clientService;
        this.registrationRepository = registrationRepository;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        ClientRegistration clientRegistration = registrationRepository.findByRegistrationId(KEYCLOAK_REGISTRATION_ID);
        Oauth2ClientCredentialsFeignManager credentialsFeignMananger =
                new Oauth2ClientCredentialsFeignManager(authorizedClientManager(), clientRegistration);
        return requestInterceptor -> {
            requestInterceptor.header("Authorization", "Bearer " + credentialsFeignMananger.getAccessToken());
        };
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager() {
        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder
                .builder()
                .clientCredentials()
                .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(registrationRepository, clientService);

        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }
}

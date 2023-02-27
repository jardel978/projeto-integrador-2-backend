package com.dmh.msusers.repository;

import com.dmh.msusers.exceptions.DataNotFoundException;
import com.dmh.msusers.exceptions.KeycloakException;
import com.dmh.msusers.exceptions.TokenException;
import com.dmh.msusers.exceptions.UserAlreadyExistException;
import com.dmh.msusers.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "dmh.keycloak")
public class KeycloakUserRepository implements IUserRepository {

    @Value("${dmh.keycloak.server-url}")
    private String serverURL;
    @Value("${dmh.keycloak.realm}")
    private String realm;
    @Value("${dmh.keycloak.client-id}")
    private String clientId;
    @Value("${dmh.keycloak.client-secret}")
    private String clientSecret;
    private final Keycloak keycloak;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User fromRepresentation(UserRepresentation userRepresentation) {
//        String password = userRepresentation.getCredentials().get(0).getSecretData();
//        log.info("password: " + password);
//        log.info("password encryptada: " + passwordEncoder.encode(password));

        return User.builder()
                .id(userRepresentation.getId())
                .name(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .cpf(userRepresentation.getAttributes().get("cpf").get(0))
                .email(userRepresentation.getEmail())
                .phone(userRepresentation.getAttributes().get("phone").get(0))
                .build(); //.password(password)
    }

    private UserRepresentation toUserRepresentation(User user) {
        Map<String, List<String>> userAttributes = new HashMap<>();
        List<String> cpfList = new ArrayList<>();
        List<String> phoneList = new ArrayList<>();
        cpfList.add(user.getCpf());
        phoneList.add(user.getPhone());

        CredentialRepresentation passwordRepresentation = new CredentialRepresentation();
        passwordRepresentation.setTemporary(false);
        passwordRepresentation.setType(CredentialRepresentation.PASSWORD);
        passwordRepresentation.setValue(user.getPassword());

        List<String> requiredActions = new ArrayList<>();
        requiredActions.add("verify email");

        userAttributes.put("cpf", cpfList);
        userAttributes.put("phone", phoneList);
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getEmail());
        userRepresentation.setFirstName(user.getName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setAttributes(userAttributes);
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setCredentials(Collections.singletonList(passwordRepresentation));
        userRepresentation.setEmailVerified(false);
        userRepresentation.setRequiredActions(requiredActions);

        List<String> defaultGroups = new ArrayList<>();
        defaultGroups.add("USERS");
        userRepresentation.setGroups(defaultGroups);

        return userRepresentation;
    }

    @Override
    public User create(User user) {
        Response response = keycloak.realm(realm).users().create(toUserRepresentation(user));
        int statusId = response.getStatus();
        if (statusId == 201) {
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            return findById(userId);
        } else if (statusId == 409) {
            throw new UserAlreadyExistException("user already exist.");
        } else {
            throw new KeycloakException("user cannot create.");
        }
    }

    @Override
    public User findById(String id) {
        try {
            UserResource userResource = keycloak
                    .realm(realm)
                    .users().get(id);
            UserRepresentation userRepresentation = userResource.toRepresentation();
            return fromRepresentation(userRepresentation);
        } catch (NotFoundException e) {
            throw new DataNotFoundException("User not found.");
        }
    }

    @Override
    public boolean updateById(String id, User user) {
        try {
            keycloak.realm(realm).users().get(id).update(toUserRepresentation(user));
            return true;
        } catch (NotFoundException e) {
            throw new DataNotFoundException("User not found.");
        }
    }


    @Override
    public AccessTokenResponse login(String email, String password) {
        Keycloak keycloakGatewayApp = KeycloakBuilder.builder().serverUrl(serverURL)
                .realm(realm).clientSecret(clientSecret).username(email).password(password)
                .clientId(clientId).build();

        return keycloakGatewayApp.tokenManager().getAccessToken();
    }

    @Override
    public void logout(HttpServletRequest servletRequest, String id) {
        try {
            List<UserSessionRepresentation> userSessionRepresentation = keycloak
                    .realm(realm)
                    .users().get(id).getUserSessions();
            userSessionRepresentation.forEach(session -> {
                log.info("session: " + session.getId());
                keycloak.realm(realm).deleteSession(session.getId());
            });
            servletRequest.logout();
        } catch (Exception exception) {
            throw new TokenException(exception.getMessage());
        }
    }

}

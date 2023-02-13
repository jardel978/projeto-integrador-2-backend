package com.dmh.msusers.repository;

import com.dmh.msusers.exceptions.DataNotFoundException;
import com.dmh.msusers.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

import javax.ws.rs.NotFoundException;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "dmh.keycloak")
public class KeycloakUserRepository implements IUserRepository {

    private final Keycloak keycloak;

    @Value("${dmh.keycloak.realm}")
    private String realm;

    private User fromRepresentation(UserRepresentation userRepresentation) {
        return User.builder()
                .id(userRepresentation.getId())
                .name(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .cpf(userRepresentation.getAttributes().get("cpf").get(0))
                .email(userRepresentation.getEmail())
                .phone(userRepresentation.getAttributes().get("phone").get(0)).build();
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
//        userRepresentation.setUsername(user.getName());
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
        return (User) keycloak.realm(realm).users().create(toUserRepresentation(user)).getEntity();
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
            throw new DataNotFoundException("Usuário não encontrado.");
        }
    }

}

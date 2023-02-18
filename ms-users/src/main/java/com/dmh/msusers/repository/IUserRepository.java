package com.dmh.msusers.repository;

import com.dmh.msusers.model.User;
import org.keycloak.representations.AccessTokenResponse;

import javax.servlet.http.HttpServletRequest;

public interface IUserRepository {

    User create(User user);

    User findById(String id);

    AccessTokenResponse login(String email, String password);

    void logout(HttpServletRequest request, String id);

}

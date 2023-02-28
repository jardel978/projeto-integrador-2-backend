package com.dmh.msusers.repository;

import com.dmh.msusers.model.User;
import com.dmh.msusers.model.dto.UserPatchDTORequest;
import org.keycloak.representations.AccessTokenResponse;

import javax.servlet.http.HttpServletRequest;

public interface IUserRepository {

    User create(User user);

    User findById(String id);

    void updateById(String id, User user);

    AccessTokenResponse login(String email, String password);

    void logout(HttpServletRequest request, String id);

}

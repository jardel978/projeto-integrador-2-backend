package com.dmh.msusers.service;

import com.dmh.msusers.model.dto.UserDTORequest;
import com.dmh.msusers.model.dto.UserDTOResponse;
import org.keycloak.representations.AccessTokenResponse;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {

    UserDTOResponse create(UserDTORequest userDTO);

    UserDTOResponse findById(String userId);

    AccessTokenResponse login(String email, String password);

    void logout(HttpServletRequest request, String token);

}

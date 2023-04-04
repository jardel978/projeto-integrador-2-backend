package com.dmh.msusers.service;

import com.dmh.msusers.model.dto.UserDTORequest;
import com.dmh.msusers.model.dto.UserDTOResponse;
import com.dmh.msusers.model.dto.UserPatchDTORequest;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {

    ResponseEntity<Object> create(UserDTORequest userDTO);

    UserDTOResponse findById(String userId);

    void updateById(String id, UserPatchDTORequest userPatch);

    AccessTokenResponse login(String email, String password);

    void logout(HttpServletRequest request, String id);

}

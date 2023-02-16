package com.dmh.msusers.service;

import com.dmh.msusers.model.User;
import com.dmh.msusers.model.dto.UserDTORequest;
import com.dmh.msusers.model.dto.UserDTOResponse;
import com.dmh.msusers.repository.KeycloakUserRepository;
import org.keycloak.representations.AccessTokenResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private KeycloakUserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    public UserDTOResponse findById(String userId) {
        User userModel = userRepository.findById(userId);
        return mapper.map(userModel, UserDTOResponse.class);
    }

    @Override
    public UserDTOResponse create(UserDTORequest userDTO) {
        User userModel = mapper.map(userDTO, User.class);
        User userSaved = userRepository.create(userModel);
        return mapper.map(userSaved, UserDTOResponse.class);
    }

    @Override
    public AccessTokenResponse login(String email, String password) {
        return userRepository.login(email, password);
    }

    @Override
    public void logout(HttpServletRequest request, String id) {
        userRepository.logout(request, id);
    }

    // TODO validar email (caso necessário e não possílvel via keycloak): userRepresentation.setEnabled(true);

}

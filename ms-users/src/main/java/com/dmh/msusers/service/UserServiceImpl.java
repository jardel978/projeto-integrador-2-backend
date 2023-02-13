package com.dmh.msusers.service;

import com.dmh.msusers.exceptions.DataNotFoundException;
import com.dmh.msusers.model.User;
import com.dmh.msusers.model.dto.UserDTORequest;
import com.dmh.msusers.model.dto.UserDTOResponse;
import com.dmh.msusers.repository.KeycloakUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // TODO validar email (caso necessário e não possílvel via keycloak): userRepresentation.setEnabled(true);

}

package com.dmh.msusers.service;

import com.dmh.msusers.exceptions.CreateAccountException;
import com.dmh.msusers.model.User;
import com.dmh.msusers.model.dto.AccountDTORequest;
import com.dmh.msusers.model.dto.UserDTORequest;
import com.dmh.msusers.model.dto.UserDTOResponse;
import com.dmh.msusers.model.dto.UserPatchDTORequest;
import com.dmh.msusers.repository.feign.IAccountsFeignRepository;
import com.dmh.msusers.repository.KeycloakUserRepository;
import com.dmh.msusers.response.ResponseHandler;
import org.keycloak.representations.AccessTokenResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private KeycloakUserRepository userRepository;

    @Autowired
    private IAccountsFeignRepository accountsFeignRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ResponseHandler responseHandler;

    public UserDTOResponse findById(String userId) {
        User userModel = userRepository.findById(userId);
        return mapper.map(userModel, UserDTOResponse.class);
    }

    @Override
    public void updateById(String id, UserPatchDTORequest userPatch) {
        User userModel = mapper.map(userPatch, User.class);
        userRepository.updateById(id, userModel);
    }

    @Override
    public ResponseEntity<Object> create(UserDTORequest userDTO) {
        User userModel;
        UserDTOResponse userDTOResponse = new UserDTOResponse();
        try {
            userModel = mapper.map(userDTO, User.class);
            User userSaved = userRepository.create(userModel);
            userDTOResponse = mapper.map(userSaved, UserDTOResponse.class);
            ResponseEntity<Map<String, Object>> responseFeign =
                    accountsFeignRepository.createAccount(AccountDTORequest.builder().userId(userSaved.getId()).build(),
                            true);

            return responseHandler.build(userDTOResponse, HttpStatus.CREATED, "User " +
                    "created successfully");
//            if (responseFeign.getBody().containsKey("error")) {
//                throw new CreateAccountException("User created but cannot create account because: " + response.getBody().get("error") +
//                        ". Try again in account service.");
//            }
        } catch (Exception ex) {
            if (ex instanceof CreateAccountException)
                return responseHandler.build(userDTOResponse, HttpStatus.CREATED, ex.getMessage());
            throw ex;
        }
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

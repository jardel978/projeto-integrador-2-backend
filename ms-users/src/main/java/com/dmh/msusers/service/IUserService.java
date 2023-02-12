package com.dmh.msusers.service;

import com.dmh.msusers.model.dto.UserDTORequest;
import com.dmh.msusers.model.dto.UserDTOResponse;

public interface IUserService {

    UserDTOResponse create(UserDTORequest userDTO);

    UserDTOResponse findById(String userId);

}

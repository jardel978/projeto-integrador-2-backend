package com.dmh.msusers.controller;

import com.dmh.msusers.exceptions.InvalidFieldException;
import com.dmh.msusers.model.dto.UserDTORequest;
import com.dmh.msusers.model.dto.UserDTOResponse;
import com.dmh.msusers.response.ResponseHandler;
import com.dmh.msusers.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ResponseHandler responseHandler;

    @PostMapping("/registration")
    public ResponseEntity<Object> create(@Valid @RequestBody UserDTORequest userDTO, BindingResult result) {

        if (result.hasErrors())
            throw new InvalidFieldException(result.getAllErrors().get(0).getDefaultMessage());

        return responseHandler.build(userService.create(userDTO), HttpStatus.CREATED, "user created successfully.");
    }

    @GetMapping("/{id}") // ...localhost:8080/users/7
    public ResponseEntity<Object> findById(@PathVariable("id") String id) {
        return responseHandler.build(userService.findById(id), HttpStatus.OK, "user found.");
    }

}

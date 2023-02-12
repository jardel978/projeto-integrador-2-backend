package com.dmh.msusers.repository;

import com.dmh.msusers.model.User;

import java.util.Optional;

public interface IUserRepository {

    User create(User user);

    Optional<User> findById(String id);

}

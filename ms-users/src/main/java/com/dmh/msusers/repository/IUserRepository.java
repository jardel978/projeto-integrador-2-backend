package com.dmh.msusers.repository;

import com.dmh.msusers.model.User;

public interface IUserRepository {

    User create(User user);

    User findById(String id);

}

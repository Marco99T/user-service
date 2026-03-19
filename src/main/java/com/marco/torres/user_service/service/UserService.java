package com.marco.torres.user_service.service;

import com.marco.torres.user_service.entity.User;

public interface UserService {

    User createUser(User user);

    User updateUser(Long id, User updatedUser);

    User getUserByUsername(String username);

    User deleteUser(String username);

}

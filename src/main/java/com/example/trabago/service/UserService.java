package com.example.trabago.service;

import com.example.trabago.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    Optional<User> getUserByEmail(String email);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByEmail(String email);
    User saveUser(User user);

    void deleteUser(User user);
}

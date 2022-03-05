package com.projectdemo.twostepverifier.service;

import com.projectdemo.twostepverifier.domain.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    User deleteUserById(int userId);
    User findUserById(int userId);
    List<User> getAllUsers();
}

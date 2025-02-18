package com.example.short_link.module.user.service;

import com.example.short_link.module.authentication.dto.LoginDTO;
import com.example.short_link.module.user.domain.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity createUser(LoginDTO dataLogin);

    UserEntity handleGetUserByUsername(String email);
    // Object handleLogin(String);

    List<UserEntity> handleGetAllUser();

}

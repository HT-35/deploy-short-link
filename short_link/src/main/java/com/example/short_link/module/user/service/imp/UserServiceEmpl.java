package com.example.short_link.module.user.service.imp;

import com.example.short_link.module.authentication.dto.LoginDTO;
import com.example.short_link.module.user.constant.RoleEnum;
import com.example.short_link.module.user.domain.UserEntity;
import com.example.short_link.module.user.repository.UserRepository;
import com.example.short_link.module.user.service.UserService;
import com.example.short_link.shared.error.ExistException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceEmpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder PASSWORD_ENCODER;

    public UserServiceEmpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        PASSWORD_ENCODER = passwordEncoder;
    }

    @Override
    public UserEntity createUser(LoginDTO dataLogin) {

        UserEntity user = this.userRepository.findByEmail(dataLogin.getEmail());


        if (user != null) {
            throw new ExistException("Account is exist !");
        }

        UserEntity userData = new UserEntity();
        userData.setRole(RoleEnum.User);
        userData.setEmail(dataLogin.getEmail());

        userData.setPassword(this.PASSWORD_ENCODER.encode(dataLogin.getPassword()));

        return this.userRepository.save(userData);
    }

    @Override
    public UserEntity handleGetUserByUsername(String email) {

        return this.userRepository.findByEmail(email);
    }

    @Override
    public List<UserEntity> handleGetAllUser() {
        return this.userRepository.findAll();
    }

}

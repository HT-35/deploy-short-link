package com.example.short_link.shared.config;

import com.example.short_link.module.user.domain.UserEntity;
import com.example.short_link.module.user.service.UserService;
import org.hibernate.mapping.Collection;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component("userDetailsService")
public class UserDetailCustom implements UserDetailsService {


    private final UserService userService;

    public UserDetailCustom( UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = this.userService.handleGetUserByUsername(username);

        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority("Role_user")
                )
        );
    }
}

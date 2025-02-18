package com.example.short_link.module.user.controller;

import com.example.short_link.module.authentication.dto.LoginDTO;
import com.example.short_link.module.user.domain.UserEntity;
import com.example.short_link.module.user.service.UserService;
import com.example.short_link.shared.annotation.SendMessage;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @SendMessage("Create User")
    @PostMapping("")
    public ResponseEntity<UserEntity> handleCreateUser(@Valid @RequestBody LoginDTO dataLogin) {
        return ResponseEntity.ok().body(this.userService.createUser(dataLogin));
    }

    // @PostMapping("/login")
    // @SendMessage("Login")
    // public ResponseEntity<UserEntity> handleLogin(@RequestBody UserEntity
    // dataUser){
    //
    // UsernamePasswordAuthenticationToken authen = new
    // UsernamePasswordAuthenticationToken(
    //
    // )
    //

    /// / return ResponseEntity.ok().body(this.userService.handleLogin);
    // }
    @GetMapping("")
    @SendMessage("Get All User")
    public ResponseEntity<Object> handleGetAllUser() {
        return ResponseEntity.ok().body(this.userService.handleGetAllUser());
    }
}

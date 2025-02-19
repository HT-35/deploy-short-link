package com.example.short_link.module.authentication.controller;

import com.example.short_link.module.authentication.dto.LoginDTO;
import com.example.short_link.module.authentication.dto.res.ResLogin;
import com.example.short_link.module.user.domain.UserEntity;
import com.example.short_link.module.user.service.UserService;
import com.example.short_link.shared.security.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    // @Value("${cookie.domain}")
    // private String domain;

    //           private final String domain = "huytranfullstack.id.vn";
    private final String domain = "localhost";

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final SecurityUtil securityUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, SecurityUtil securityUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLogin> handleLogin(@Valid @RequestBody LoginDTO dataLogin) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dataLogin.getEmail(), dataLogin.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = this.userService.handleGetUserByUsername(dataLogin.getEmail());

        ResLogin resLogin = new ResLogin();
        String accessToken = this.securityUtil.createAccessToken(dataLogin.getEmail(), user);

        resLogin.setToken(accessToken);
        ResLogin.DataUser dataUser = new ResLogin.DataUser();
        dataUser.setEmail(user.getEmail());
        dataUser.setRole(String.valueOf(user.getRole()));

        resLogin.setData(dataUser);


        // Tạo cookie
        ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie
                .from("accessToken", accessToken)
                .httpOnly(false).path("/")
                .maxAge(30 * 24 * 60 * 60)
                .domain(domain);

        // Không sử dụng secure() vì bạn chạy trên HTTP
        ResponseCookie resCookie = cookieBuilder.build();


        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookie.toString()).body(resLogin);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> handleRegister(@Valid @RequestBody LoginDTO dataLogin) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(this.userService.createUser(dataLogin));

//        return null;
    }
}

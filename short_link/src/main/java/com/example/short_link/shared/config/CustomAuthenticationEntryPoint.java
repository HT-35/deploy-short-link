package com.example.short_link.shared.config;

import com.example.short_link.shared.response.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    private final ObjectMapper mapper;

    public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        RestResponse<Object> res = new RestResponse<>();

        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());

        // Kiểm tra lỗi
        String errorMessage;

        if(authException.getMessage().contains("Full authentication is required")){
            errorMessage = "Full authentication is required to access this resource";
            res.setMessage("Token không được truyền hoặc không hợp lệ (JWT không đúng định dạng).");
        }else if(authException.getMessage().contains("Cannot invoke")){
            errorMessage = "Cannot process user information.";
            res.setMessage("Sai username hoặc password, vui lòng kiểm tra lại.");
        } else {
            errorMessage = authException.getMessage();
            res.setMessage("Token không hợp lệ (hết hạn, không đúng định dạng, hoặc không truyền JWT ở header).");
        }

        res.setError(errorMessage);

        // Ghi JSON response
        mapper.writeValue(response.getWriter(), res);

    }
}

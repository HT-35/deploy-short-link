package com.example.short_link.shared.error;

import com.example.short_link.shared.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<RestResponse<Object>> handleAuthenticationException(AuthenticationException ex,   MethodParameter returnType) {
//
//
//
//        RestResponse<Object> res = new RestResponse();
//        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());
//        res.setMessage(returnType.getMethodAnnotation(SendMessage.class));
//        res.setError(ex.getMessage());
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(res);
//    }


    @ExceptionHandler(value = {
            UsernameNotFoundException.class, // Khi không tìm thấy user trong hệ thống
            BadCredentialsException.class, // Khi thông tin đăng nhập không chính xác
            MissingRequestCookieException.class
    })

    public ResponseEntity<RestResponse<Object>> handleAuthenticationException(Exception ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.UNAUTHORIZED.value()); // Trả về mã HTTP 401 (Unauthorized)
        res.setError(ex.getClass().getSimpleName()); // Đưa tên lớp ngoại lệ để hỗ trợ việc debug
        res.setMessage(ex.getMessage()); // Trả về thông báo chi tiết từ ngoại lệ
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res); // Trả về đối tượng ResponseEntity với thông tin
        // lỗi
    }

    @ExceptionHandler(value = {
            ExistException.class,
    })

    public ResponseEntity<RestResponse<Object>> handleExistException(Exception ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setMessage(ex.getMessage());
        res.setError(ex.getClass().getSimpleName());
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(res);


    }


    @ExceptionHandler(
            value = {
                    NotFoundException.class
            }
    )
    public ResponseEntity<RestResponse<Object>> handleNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(
                        new RestResponse<Object>(
                                HttpStatus.BAD_REQUEST.value(),
                                ex.getClass().getSimpleName(),
                                ex.getMessage(),
                                null
                        )
                );
    }
}

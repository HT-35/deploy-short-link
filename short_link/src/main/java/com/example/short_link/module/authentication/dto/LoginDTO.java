package com.example.short_link.module.authentication.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "Please enter your email")
    private String email;

    @NotBlank(message = "Please enter your email")
    private String password;
}

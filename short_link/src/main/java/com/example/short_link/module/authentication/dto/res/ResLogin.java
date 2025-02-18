package com.example.short_link.module.authentication.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResLogin {
    private String token;
    private DataUser data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DataUser {
        private String email;
        private String role;
    }
}

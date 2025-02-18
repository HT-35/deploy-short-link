package com.example.short_link.shared.security;

import com.example.short_link.module.user.domain.UserEntity;
import com.nimbusds.jose.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class SecurityUtil {

    private final JwtEncoder jwtEncoder;

    public SecurityUtil(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS256;

    @Value("${jwt.base64-secret}")
    private String jwtKey;

    @Value("${access-token-validity-in-second}")
    private long accessTokenExpiration;

    @Value("${refresh-token-validity-in-second}")
    private long refreshToeknExpiration;

    public String createAccessToken(String email, UserEntity user) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);

        String role = user.getRole() != null ? user.getRole().toString() : "client";

        String authority = "ROLE_" + role;

        JwtClaimsSet claims = JwtClaimsSet
                .builder().issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .claim("authorities", authority)
                .claim("user", user.getEmail())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public String createRefreshToken(String email, UserEntity user) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.refreshToeknExpiration, ChronoUnit.SECONDS);

        JwtClaimsSet claims = JwtClaimsSet
                .builder().issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .claim("user", user.getEmail())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();

        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JWT_ALGORITHM.getName());
    }

    public Jwt checkValidRefeshToken(String token) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder
                .withSecretKey(getSecretKey())
                .macAlgorithm(SecurityUtil.JWT_ALGORITHM).build();

        try {
            return jwtDecoder.decode(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

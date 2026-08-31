package com.duong.ecommerce.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;

public interface JwtService {

    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String extractUsername(String token);

    boolean isTokenValid(String username, UserDetails userDetails);

    Instant extractExpDate(String refreshToken);
}

package com.duong.ecommerce.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;

public interface JwtService {

    String generateAccessToken(MyUserDetails userDetails);

    String generateRefreshToken(MyUserDetails userDetails);

    String extractUsername(String token);

    Claims extractAll(String token);

    Instant extractExpDate(String refreshToken);
}

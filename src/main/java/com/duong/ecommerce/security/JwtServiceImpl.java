package com.duong.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService{
    @Value("${jwt.secretkey}")
    private String secretKey;

    private long accessExp = 15 * 60 * 1000;

    private long refreshExp = 15 * 60 * 1000;


    @Override
    public String generateAccessToken(UserDetails userDetails) {
        Map<String,Object> claims = new HashMap<>();
        Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        claims.put("roles", roles);
        return buildToken(claims,userDetails,accessExp);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(),userDetails,refreshExp);
    }

    private String buildToken(Map<String,Object> claims, UserDetails userDetails , long exp){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .signWith(getKey())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plusMillis(exp)))
                .compact();

    }

    @Override
        public String extractUsername(String token) {
            return extractAll(token).getSubject();
        }


    public Claims extractAll(String token){
        try {


            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)//parse và verify chữ ký
                    .getPayload();
        } catch (SignatureException e){
            throw new InvalidKeyException("Key is invalid");
        }
    }


    private SecretKey getKey(){

        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte); // dùng thuật toán để tạo SecretKey keyBytes

    }

    @Override
    public boolean isTokenValid(String username, UserDetails userDetails) {

        return username.equals(userDetails.getUsername());
    }

    @Override
    public Instant extractExpDate(String refreshToken) {
        return extractAll(refreshToken).getExpiration().toInstant();
    }
}

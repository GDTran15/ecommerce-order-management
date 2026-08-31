package com.duong.ecommerce.user.repository;

import com.duong.ecommerce.user.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken);

    Optional<RefreshToken> findByToken(String s);

    void revokedByUserId(Long userId);
}

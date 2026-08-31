package com.duong.ecommerce.user.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken

) {
}

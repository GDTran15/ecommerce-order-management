package com.duong.ecommerce.user.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class RefreshToken {
    private Long id;
    private String token;
    private Long userId;
    private Instant expiredDate;
    private Boolean revoked;
    private Instant createdAt;

}

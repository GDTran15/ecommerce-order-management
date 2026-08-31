package com.duong.ecommerce.user.repository;

import com.duong.ecommerce.user.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImp implements  RefreshTokenRepository{

    private final JdbcTemplate jdbcTemplate;
    @Override
    public void save(RefreshToken refreshToken) {
        String sql = """
                INSERT INTO refresh_token (token,user_id,expired_date) VALUES (?,?,?)
                """;

        jdbcTemplate.update(sql,refreshToken.getToken(),refreshToken.getUserId(), Timestamp.from(refreshToken.getExpiredDate()));
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        String sql = """
            SELECT *  FROM refresh_token  WHERE token = ?
            """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new RefreshToken(
                        rs.getLong("id"),
                        rs.getString("token"),
                        rs.getLong("user_id"),
                        rs.getTimestamp("expires_at").toInstant(),
                        rs.getBoolean("revoked"),
                        rs.getTimestamp("created_at").toInstant()
                ),
                token
        ).stream().findFirst();
    }

    @Override
    public void revokedByUserId(Long userId) {
        String sql = """
                UPDATE refresh_token SET revoked = true WHERE user_id = ? AND revoked = false;
                """;

        jdbcTemplate.update(sql,userId);
    }
}

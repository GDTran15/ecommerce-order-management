package com.duong.ecommerce.repoImp;

import com.duong.ecommerce.exception.ResourceNotFoundException;
import com.duong.ecommerce.model.User;
import com.duong.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class UserRepositoryImp implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findByUsername(String username) {

        String sql = """
                SELECT *
                FROM users
                WHERE username = ?
                """;

        List<User> users = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> User.builder()
                        .id(rs.getLong("id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .email(rs.getString("email"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .phoneNumber(rs.getString("phone_number"))
                        .dateOfBirth(rs.getDate("date_of_birth").toLocalDate())
                        .createdAt(rs.getTimestamp("created_at").toInstant())
                        .build(),
                username
        );

        return users.stream().findFirst();
    }

    @Override
    public List<User> findAll() {

        String sql = """
                SELECT *
                FROM users
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> User.builder()
                        .id(rs.getLong("id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .email(rs.getString("email"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .phoneNumber(rs.getString("phone_number"))
                        .dateOfBirth(rs.getDate("date_of_birth").toLocalDate())
                        .createdAt(rs.getTimestamp("created_at").toInstant())
                        .build()
        );
    }

    @Override
    public Long save(User user) {

        String sql = """
                INSERT INTO users(
                    username,
                    password,
                    email,
                    first_name,
                    last_name,
                    phone_number,
                    date_of_birth
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                RETURNING id
                """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                Date.valueOf(user.getDateOfBirth())
        );
    }

    @Override
    public void deleteUserByUsername(String username) {

        String sql = """
                DELETE FROM users
                WHERE username = ?
                """;

        jdbcTemplate.update(sql, username);
    }

    @Override
    public void updateUser(Long userId, User user) {

        String sql = """
                UPDATE users
                SET username = ?,
                    password = ?,
                    email = ?,
                    first_name = ?,
                    last_name = ?,
                    phone_number = ?,
                    date_of_birth = ?
                WHERE id = ?
                """;

        int rows = jdbcTemplate.update(
                sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                Date.valueOf(user.getDateOfBirth()),
                userId
        );

        if (rows == 0) {
            throw new ResourceNotFoundException("User is not existed");
        }
    }

    @Override
    public boolean existsByUsername(String username) {

        String sql = """
                SELECT EXISTS(
                    SELECT 1
                    FROM users
                    WHERE username = ?
                )
                """;

        Boolean exists = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                username
        );

        return Boolean.TRUE.equals(exists);
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = """
                SELECT EXISTS(
                    SELECT 1
                       FROM users
                       WHERE email = ?
                )
                """;

        Boolean exists  = jdbcTemplate.queryForObject(sql, Boolean.class,email);

        return Boolean.TRUE.equals(exists);
    }
    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        String sql = """
                SELECT EXISTS(
                    SELECT 1
                       FROM users
                       WHERE phone_number = ?
                )
                """;

        Boolean exists  = jdbcTemplate.queryForObject(sql, Boolean.class,phoneNumber);

        return Boolean.TRUE.equals(exists);
    }
}
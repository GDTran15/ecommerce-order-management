package com.duong.ecommerce.user.repository;

import com.duong.ecommerce.user.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRoleRepositoryImp implements UserRoleRepository{

    private final JdbcTemplate jdbcTemplate;



    @Override
    public void save(UserRole userRole) {
        String sql = """
                INSERT INTO user_roles (user_id,role_id) VALUES (?,?)
                """;

        jdbcTemplate.update(sql,userRole.getUserId(),userRole.getRoleId());
    }
}

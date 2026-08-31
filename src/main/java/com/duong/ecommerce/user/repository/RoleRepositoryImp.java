package com.duong.ecommerce.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImp implements RoleRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long findIdByName(String roleName) {
       String sql = "SELECT id FROM roles WHERE name = ?";

       return jdbcTemplate.queryForObject(sql,Long.class,roleName);
    }
}

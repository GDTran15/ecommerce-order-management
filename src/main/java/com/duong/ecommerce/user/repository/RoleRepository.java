package com.duong.ecommerce.user.repository;

public interface RoleRepository {

    Long findIdByName(String roleName);
}

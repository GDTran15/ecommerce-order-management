package com.duong.ecommerce.repository;

import com.duong.ecommerce.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
     Optional<User> findByUsername(String username);

     List<User> findAll();

     void save(User user);

     void deleteUserByUsername(String username);

     void updateUser(Long userId,User user);
}

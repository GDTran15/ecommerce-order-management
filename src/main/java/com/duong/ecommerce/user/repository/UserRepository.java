package com.duong.ecommerce.user.repository;

import com.duong.ecommerce.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
     Optional<User> findByUsername(String username);

     List<User> findAll();

     Long save(User user);

     void deleteUserByUsername(String username);

     void updateUser(Long userId,User user);

     boolean existsByUsername(String username);

     boolean existsByEmail(String email);

     boolean existsByPhoneNumber(String phoneNumber);

    void bumpTokenVersion(Long userId);
}

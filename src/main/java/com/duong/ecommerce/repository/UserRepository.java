package com.duong.ecommerce.repository;

import com.duong.ecommerce.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    public Optional<User> findByUsername(String username);

    public List<User> findAll();

    public void save(User user);


}

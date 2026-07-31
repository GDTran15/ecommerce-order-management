package com.duong.ecommerce.service;


import com.duong.ecommerce.dto.user.request.CreateNewUserRequest;

public interface UserService {

     void createUser(CreateNewUserRequest createNewUserRequest);
}

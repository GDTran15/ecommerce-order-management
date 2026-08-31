package com.duong.ecommerce.user.service;


import com.duong.ecommerce.user.dto.CreateNewUserRequest;
import com.duong.ecommerce.user.dto.LoginRequest;
import com.duong.ecommerce.user.dto.GetUserResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     Long createUser(CreateNewUserRequest createNewUserRequest);

     void deleteUser(String username);

     GetUserResponse getUser(String username);

     List<GetUserResponse> getAllUser();

     void updateUser(Long userId, CreateNewUserRequest createNewUserRequest);

     void loginUser(LoginRequest request);
}

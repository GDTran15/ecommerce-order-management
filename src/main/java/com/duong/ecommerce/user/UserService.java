package com.duong.ecommerce.user;


import com.duong.ecommerce.user.dto.CreateNewUserRequest;
import com.duong.ecommerce.user.dto.LoginRequest;
import com.duong.ecommerce.user.dto.GetUserResponse;

import java.util.List;

public interface UserService {

     Long createUser(CreateNewUserRequest createNewUserRequest);

     void deleteUser(String username);

     GetUserResponse getUser(String username);

     List<GetUserResponse> getAllUser();

     void updateUser(Long userId, CreateNewUserRequest createNewUserRequest);

     void loginUser(LoginRequest request);
}

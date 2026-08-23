package com.duong.ecommerce.service;


import com.duong.ecommerce.dto.user.request.CreateNewUserRequest;
import com.duong.ecommerce.dto.user.response.GetUserResponse;

import java.util.List;

public interface UserService {

     Long createUser(CreateNewUserRequest createNewUserRequest);

     void deleteUser(String username);

     GetUserResponse getUser(String username);

     List<GetUserResponse> getAllUser();

     void updateUser(Long userId, CreateNewUserRequest createNewUserRequest);
}

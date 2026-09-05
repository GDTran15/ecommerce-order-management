package com.duong.ecommerce.user.service;

import com.duong.ecommerce.user.dto.*;
import jakarta.validation.Valid;

public interface AuthenticationService {


    void registerUser(CreateNewUserRequest request);

    LoginResponse loginUser(@Valid LoginRequest request);

    RefreshResponse getAccessToken(RefreshRequest request);

    void logoutUser(RefreshRequest request);

    void logoutAllUser();

}

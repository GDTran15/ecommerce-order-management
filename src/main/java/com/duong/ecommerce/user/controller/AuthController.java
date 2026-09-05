package com.duong.ecommerce.user.controller;

import com.duong.ecommerce.common.validation.OnCreate;
import com.duong.ecommerce.user.dto.*;
import com.duong.ecommerce.user.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        LoginResponse response = authService.loginUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Validated(OnCreate.class) CreateNewUserRequest request){
        authService.registerUser(request);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@RequestBody RefreshRequest request){
        RefreshResponse response = authService.getAccessToken(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshRequest request){
        authService.logoutUser(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout/all-devices")
    public ResponseEntity<Void> logoutAll(){
        authService.logoutAllUser();
        return ResponseEntity.noContent().build();
    }

}

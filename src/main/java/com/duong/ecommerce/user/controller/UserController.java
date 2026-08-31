package com.duong.ecommerce.user.controller;

import com.duong.ecommerce.user.service.UserService;
import com.duong.ecommerce.user.dto.CreateNewUserRequest;
import com.duong.ecommerce.user.dto.LoginRequest;
import com.duong.ecommerce.user.dto.GetUserResponse;
import com.duong.ecommerce.common.validation.OnCreate;
import com.duong.ecommerce.common.validation.OnUpdate;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;



    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@RequestParam String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/username")
    public ResponseEntity<GetUserResponse> getUser(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody @Validated(OnUpdate.class) CreateNewUserRequest createNewUserRequest) {
        userService.updateUser(userId, createNewUserRequest);
        return ResponseEntity.ok().build();
    }

}

package com.duong.ecommerce.controller;

import com.duong.ecommerce.dto.user.request.CreateNewUserRequest;
import com.duong.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateNewUserRequest createNewUserRequest) {
         userService.createUser(createNewUserRequest);
         return ResponseEntity.ok().build();
    }

}

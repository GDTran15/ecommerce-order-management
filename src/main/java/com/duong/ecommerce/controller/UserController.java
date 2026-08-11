package com.duong.ecommerce.controller;

import com.duong.ecommerce.dto.user.request.CreateNewUserRequest;
import com.duong.ecommerce.dto.user.response.GetUserResponse;
import com.duong.ecommerce.model.User;
import com.duong.ecommerce.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateNewUserRequest createNewUserRequest) {
         userService.createUser(createNewUserRequest);
         return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@RequestParam String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<GetUserResponse> getUser(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody @Valid CreateNewUserRequest createNewUserRequest) {
        userService.updateUser(userId, createNewUserRequest);
        return ResponseEntity.ok().build();
    }

}

package com.duong.ecommerce.user;

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

    @PostMapping()
    public ResponseEntity<Void> createUser(@RequestBody @Validated(OnCreate.class) CreateNewUserRequest createNewUserRequest) {
        Long id =  userService.createUser(createNewUserRequest);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(id).toUri();
         return ResponseEntity.created(uri).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request){
        userService.loginUser(request);
        return ResponseEntity.noContent().build();
    }

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

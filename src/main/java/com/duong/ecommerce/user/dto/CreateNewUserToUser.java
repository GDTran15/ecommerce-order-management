package com.duong.ecommerce.user.dto;

import com.duong.ecommerce.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CreateNewUserToUser implements Function<CreateNewUserRequest, User> {

    private final PasswordEncoder passwordEncoder;


    @Override
    public User apply(CreateNewUserRequest createNewUserRequest) {
        return User.builder()
                .username(createNewUserRequest.username())
                .password(passwordEncoder.encode(createNewUserRequest.password()))
                .email(createNewUserRequest.email())
                .firstName(createNewUserRequest.firstName())
                .lastName(createNewUserRequest.lastName())
                .phoneNumber(createNewUserRequest.phoneNumber())
                .dateOfBirth(createNewUserRequest.dateOfBirth())
                .build();
    }
}

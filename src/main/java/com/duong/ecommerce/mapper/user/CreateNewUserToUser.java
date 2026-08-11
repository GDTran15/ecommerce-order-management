package com.duong.ecommerce.mapper.user;

import com.duong.ecommerce.dto.user.request.CreateNewUserRequest;
import com.duong.ecommerce.model.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CreateNewUserToUser implements Function<CreateNewUserRequest, User> {


    @Override
    public User apply(CreateNewUserRequest createNewUserRequest) {
        return User.builder()
                .username(createNewUserRequest.username())
                .password(createNewUserRequest.password())
                .email(createNewUserRequest.email())
                .firstName(createNewUserRequest.firstName())
                .lastName(createNewUserRequest.lastName())
                .phoneNumber(createNewUserRequest.phoneNumber())
                .dateOfBirth(createNewUserRequest.dateOfBirth())
                .build();
    }
}

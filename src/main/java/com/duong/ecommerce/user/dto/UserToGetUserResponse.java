package com.duong.ecommerce.user.dto;

import com.duong.ecommerce.user.model.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserToGetUserResponse implements Function<User, GetUserResponse> {
    @Override
    public GetUserResponse apply(User user) {
        return new GetUserResponse(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), user.getDateOfBirth());
    }
}

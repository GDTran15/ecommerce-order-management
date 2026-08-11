package com.duong.ecommerce.serviceImp;

import com.duong.ecommerce.dto.user.request.CreateNewUserRequest;
import com.duong.ecommerce.dto.user.response.GetUserResponse;
import com.duong.ecommerce.exception.ResourceNotFoundException;
import com.duong.ecommerce.mapper.user.CreateNewUserToUser;
import com.duong.ecommerce.mapper.user.UserToGetUserResponse;
import com.duong.ecommerce.model.User;
import com.duong.ecommerce.repository.UserRepository;
import com.duong.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final UserToGetUserResponse userToGetUserResponse;

    @Override
    public void createUser(CreateNewUserRequest createNewUserRequest) {
        User user = User.builder()
                .username(createNewUserRequest.username())
                .password(createNewUserRequest.password())
                .email(createNewUserRequest.email())
                .firstName(createNewUserRequest.firstName())
                .lastName(createNewUserRequest.lastName())
                .phoneNumber(createNewUserRequest.phoneNumber())
                .dateOfBirth(createNewUserRequest.dateOfBirth())
                .build();
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
    }

    @Override
    public GetUserResponse getUser(String username) {
        User user =userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User is not existed"));

        return userToGetUserResponse.apply(user);
    }

    @Override
}

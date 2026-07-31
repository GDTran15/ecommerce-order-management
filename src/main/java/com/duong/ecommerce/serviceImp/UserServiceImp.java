package com.duong.ecommerce.serviceImp;

import com.duong.ecommerce.dto.user.request.CreateNewUserRequest;
import com.duong.ecommerce.model.User;
import com.duong.ecommerce.repository.UserRepository;
import com.duong.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

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
}

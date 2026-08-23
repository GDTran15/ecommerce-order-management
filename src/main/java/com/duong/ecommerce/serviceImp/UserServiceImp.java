package com.duong.ecommerce.serviceImp;

import com.duong.ecommerce.dto.user.request.CreateNewUserRequest;
import com.duong.ecommerce.dto.user.response.GetUserResponse;
import com.duong.ecommerce.exception.ResourceNotFoundException;
import com.duong.ecommerce.exception.UserAlreadyExistedException;
import com.duong.ecommerce.mapper.user.CreateNewUserToUser;
import com.duong.ecommerce.mapper.user.UserToGetUserResponse;
import com.duong.ecommerce.model.User;
import com.duong.ecommerce.repository.UserRepository;
import com.duong.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final UserToGetUserResponse userToGetUserResponse;
    private final CreateNewUserToUser createNewUserToUser;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12); // tạm thời như vậy nếu sau này có authentication sửa lại

    @Override
    public Long createUser(CreateNewUserRequest createNewUserRequest) {
        checkIfUserExist(createNewUserRequest.username(), createNewUserRequest.email(), createNewUserRequest.phoneNumber());
        User user = createNewUserToUser.apply(createNewUserRequest);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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
    public List<GetUserResponse> getAllUser() {
        return userRepository.findAll().stream().map(userToGetUserResponse).toList();
    }

    @Override
    public void updateUser(Long userId, CreateNewUserRequest createNewUserRequest) {
        User user = createNewUserToUser.apply(createNewUserRequest);
        //phần password này nên xem lại
        userRepository.updateUser(userId, user);
    }

    private void checkIfUserExist(String username,String email, String phoneNumber){
        boolean usernameExisted = userRepository.existsByUsername(username);
        boolean emailExisted = userRepository.existsByEmail(email);
        boolean phoneNumberExisted = userRepository.existsByPhoneNumber(phoneNumber);
        if (usernameExisted){
            throw new UserAlreadyExistedException("Username already existed");
        }
        if (emailExisted){
            throw new UserAlreadyExistedException("Username already existed");
        }
        if (phoneNumberExisted){
            throw new UserAlreadyExistedException("Username already existed");
        }
    }
}

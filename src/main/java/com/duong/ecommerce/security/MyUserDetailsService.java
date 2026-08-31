package com.duong.ecommerce.auth;

import com.duong.ecommerce.exception.ResourceNotFoundException;
import com.duong.ecommerce.user.User;
import com.duong.ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Username " + username + " not found")
        );
        return new MyUserDetails(user);
    }
}

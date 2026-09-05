package com.duong.ecommerce.user.service;

import com.duong.ecommerce.exception.InvalidTokenException;
import com.duong.ecommerce.exception.UserAlreadyExistedException;
import com.duong.ecommerce.security.JwtService;
import com.duong.ecommerce.security.MyUserDetails;
import com.duong.ecommerce.user.dto.*;
import com.duong.ecommerce.user.model.RefreshToken;
import com.duong.ecommerce.user.model.RoleName;
import com.duong.ecommerce.user.model.User;
import com.duong.ecommerce.user.model.UserRole;
import com.duong.ecommerce.user.repository.RefreshTokenRepository;
import com.duong.ecommerce.user.repository.RoleRepository;
import com.duong.ecommerce.user.repository.UserRepository;
import com.duong.ecommerce.user.repository.UserRoleRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService{

    private final UserRepository userRepo;
    private final CreateNewUserToUser toUser;
    private final RoleRepository roleRepo;
    private final RefreshTokenRepository refreshTokenRepo;
    private final UserRoleRepository userRoleRepo;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private void checkIfUserExist(String username,String email, String phoneNumber){
        boolean usernameExisted = userRepo.existsByUsername(username);
        boolean emailExisted = userRepo.existsByEmail(email);
        boolean phoneNumberExisted = userRepo.existsByPhoneNumber(phoneNumber);
        if (usernameExisted){
            throw new UserAlreadyExistedException("Username already existed");
        }
        if (emailExisted){
            throw new UserAlreadyExistedException("Email already existed");
        }
        if (phoneNumberExisted){
            throw new UserAlreadyExistedException("Phone already existed");
        }
    }

    @Override
    @Transactional
    public void registerUser(CreateNewUserRequest request) {
        checkIfUserExist(request.username(), request.email(), request.phoneNumber());

        User user = toUser.apply(request);

        Long userId = userRepo.save(user);

        Long roleId = roleRepo.findIdByName(RoleName.CUSTOMER.name());

        UserRole userRole= new UserRole(userId,roleId);

        userRoleRepo.save(userRole);

    }

    @Override
    public LoginResponse loginUser(LoginRequest request) {
        Authentication auth = UsernamePasswordAuthenticationToken.unauthenticated(request.username(),request.password());
        auth = authenticationManager.authenticate(auth);

        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        String accessToken = jwtService.generateAccessToken(myUserDetails);
        String refreshToken = jwtService.generateRefreshToken(myUserDetails);


        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .expiredDate(jwtService.extractExpDate(refreshToken))
                .userId(myUserDetails.getUserId())
                .build();

        refreshTokenRepo.save(token);

        return new LoginResponse(accessToken,refreshToken);
    }

    @Override
    public RefreshResponse getAccessToken(RefreshRequest request) {
        RefreshToken refreshToken = refreshTokenRepo.findByToken(request.refreshToken())
                .orElseThrow(() -> new InvalidTokenException("Refresh token invalid") );

        Claims claims = jwtService.extractAll(refreshToken.getToken());

        Integer tokenVersion = claims.get("token_version", Integer.class);

        String username = claims.getSubject();

        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(username);

        if (refreshToken.getRevoked() || !Objects.equals(tokenVersion,userDetails.getTokenVersion())){
            throw new InvalidTokenException("Refresh token has been revoked");
        }

        String accessToken = jwtService.generateAccessToken(userDetails);

        return new RefreshResponse(accessToken);
    }

    @Override
    public void logoutUser(RefreshRequest request) {

        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int assertRow= refreshTokenRepo.revokedByTokenAndUserId(request.refreshToken(),myUserDetails.getUserId());
        if (assertRow == 0){
            throw new InvalidTokenException("Token invalid");
        }
    }

    @Override
    public void logoutAllUser() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userRepo.bumpTokenVersion(myUserDetails.getUserId());
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void cleanUpExpiredToken(){

        refreshTokenRepo.deleteExpiredToken();
    }



}

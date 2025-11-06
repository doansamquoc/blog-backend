package com.sam.blog_user.service.impl;

import com.sam.blog_core.service.JwtService;
import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.exception.BusinessException;
import com.sam.blog_user.dto.request.UserUpdateRequest;
import com.sam.blog_user.dto.response.UserResponse;
import com.sam.blog_user.entity.User;
import com.sam.blog_user.mapper.UserMapper;
import com.sam.blog_user.repository.UserRepository;
import com.sam.blog_user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;

    @Override
    public UserResponse me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            Jwt jwt = (Jwt) auth.getPrincipal();
            User user = userRepository.findByUsername(jwt.getSubject()).orElseThrow(
                    () -> new BusinessException(ErrorCode.USER_NOT_FOUND)
            );
            return userMapper.toUserResponse(user);
        }
        throw new BusinessException(ErrorCode.USER_NOT_LOGGED_IN);
    }

    @Override
    public User authenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            Jwt jwt = (Jwt) auth.getPrincipal();
            return userRepository.findByUsername(jwt.getSubject()).orElseThrow(
                    () -> new BusinessException(ErrorCode.USER_NOT_FOUND)
            );
        }
        throw new BusinessException(ErrorCode.USER_NOT_LOGGED_IN);
    }

    @Override
    public UserResponse updateById(Long id, UserUpdateRequest r, HttpServletRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user = userMapper.toUserUpdateRequest(r, user);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmailAddress(String email) {
        return userRepository.existsByEmailAddress(email);
    }
}

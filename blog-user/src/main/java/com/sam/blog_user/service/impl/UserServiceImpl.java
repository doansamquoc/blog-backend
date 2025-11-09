package com.sam.blog_user.service.impl;

import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.exception.BusinessException;
import com.sam.blog_user.dto.request.UserDeleteRequest;
import com.sam.blog_user.dto.request.UserUpdateRequest;
import com.sam.blog_user.dto.response.UserResponse;
import com.sam.blog_user.entity.User;
import com.sam.blog_user.mapper.UserMapper;
import com.sam.blog_user.repository.UserRepository;
import com.sam.blog_user.service.UserService;
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

    @Override
    public UserResponse me(String username) {
        User user = findUserByUsername(username);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse update(String username, UserUpdateRequest request) {
        User user = findUserByUsername(username);
        user = userMapper.updateUserFromRequest(request, user);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public void delete(String username, UserDeleteRequest request) {
        User user = findUserByUsername(username);
        if (!passwordEncoder.matches(request.getPassword(), user.getHashedPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }
        userRepository.delete(user);
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
    public void save(User user) {
        userRepository.save(user);
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

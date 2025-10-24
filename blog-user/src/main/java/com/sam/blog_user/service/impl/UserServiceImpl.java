package com.sam.blog_user.service.impl;

import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.exception.BusinessException;
import com.sam.blog_user.dto.request.UserCreationRequest;
import com.sam.blog_user.dto.response.UserResponse;
import com.sam.blog_user.entity.User;
import com.sam.blog_user.enums.Role;
import com.sam.blog_user.mapper.UserMapper;
import com.sam.blog_user.repository.UserRepository;
import com.sam.blog_user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreationRequest r) {
        if (userRepository.existsByEmailAddress(r.getEmailAddress()))
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);

        if (userRepository.existsByUsername(r.getUsername()))
            throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS);

        User user = userMapper.toUserCreationRequest(r);

        // Initial role
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);

        // Set role
        user.setRoles(roles);

        // Hash password
        String hashedPassword = passwordEncoder.encode(r.getPassword());

        // Set hashed password
        user.setHashedPassword(hashedPassword);

        // Save user
        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }
}

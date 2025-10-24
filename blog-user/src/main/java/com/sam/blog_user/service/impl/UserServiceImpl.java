package com.sam.blog_user.service.impl;

import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.exception.BusinessException;
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
}

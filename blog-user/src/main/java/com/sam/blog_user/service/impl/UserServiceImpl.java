package com.sam.blog_user.service.impl;

import com.sam.blog_core.service.JwtService;
import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.exception.BusinessException;
import com.sam.blog_user.dto.request.ForgetPasswordRequest;
import com.sam.blog_user.dto.request.UserUpdatePasswordRequest;
import com.sam.blog_user.dto.request.UserUpdateRequest;
import com.sam.blog_user.dto.response.UserResponse;
import com.sam.blog_user.entity.User;
import com.sam.blog_user.event.PasswordChangedEvent;
import com.sam.blog_user.mapper.UserMapper;
import com.sam.blog_user.repository.UserRepository;
import com.sam.blog_user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
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
    ApplicationEventPublisher eventPublisher;
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
    public UserResponse updateById(Long id, UserUpdateRequest r, HttpServletRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user = userMapper.toUserUpdateRequest(r, user);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public void updatePassword(UserUpdatePasswordRequest r, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) auth.getPrincipal();
        User user = userRepository.findByUsername(jwt.getSubject()).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND)
        );

        // If password do not match throw an error
        if (!passwordEncoder.matches(r.getOldPassword(), user.getHashedPassword()))
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);

        String hashedNewPassword = passwordEncoder.encode(r.getNewPassword());
        user.setHashedPassword(hashedNewPassword);
        User userSaved = userRepository.save(user);

        PasswordChangedEvent event = new PasswordChangedEvent(this, userSaved.getEmailAddress(), request);
        eventPublisher.publishEvent(event);
    }

    public void resetPassword(String token) {

    }

    public void forgetPassword(ForgetPasswordRequest r, HttpServletRequest request) {

    }
}

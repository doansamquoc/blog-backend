package com.sam.blog_auth.service.impl;

import com.sam.blog_auth.entity.PasswordResetToken;
import com.sam.blog_auth.repository.PasswordResetTokenRepository;
import com.sam.blog_auth.service.PasswordResetTokenService;
import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.exception.BusinessException;
import com.sam.blog_user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    PasswordResetTokenRepository repository;
    UserService userService;
    PasswordEncoder passwordEncoder;

    ApplicationEventPublisher eventPublisher;

    static long EXPIRATION_TIME_MS = 900000; // 15 minutes

    @Override
    public PasswordResetToken findByTokenValue(String token) {
        return repository.findByTokenValue(token).orElseThrow(() -> new BusinessException(ErrorCode.INVALID_TOKEN));
    }

    @Override
    public void verifyPasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = this.findByTokenValue(token);
        boolean isExpired = passwordResetToken.getExpiryDate().isBefore(Instant.now());
        boolean isUsed = passwordResetToken.isUsed();

        if (isExpired)
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        if (isUsed)
            throw new BusinessException(ErrorCode.TOKEN_USED);
    }

    @Override
    public void verifyPasswordResetToken(PasswordResetToken passwordResetToken) {
        boolean isExpired = passwordResetToken.getExpiryDate().isBefore(Instant.now());
        boolean isUsed = passwordResetToken.isUsed();

        if (isExpired)
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        if (isUsed)
            throw new BusinessException(ErrorCode.TOKEN_USED);
    }

    @Override
    public void makeUsed(String token) {
        PasswordResetToken passwordResetToken = this.findByTokenValue(token);
        passwordResetToken.setUsed(true);
        repository.save(passwordResetToken);
    }

    @Override
    public void delete(String token) {
        PasswordResetToken passwordResetToken = this.findByTokenValue(token);
        repository.delete(passwordResetToken);
    }

    @Override
    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        return repository.save(passwordResetToken);
    }
}

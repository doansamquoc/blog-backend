package com.sam.blog_auth.service;
import com.sam.blog_auth.entity.PasswordResetToken;

public interface PasswordResetTokenService {
    PasswordResetToken findByTokenValue(String token);

    void verifyPasswordResetToken(String token);

    void verifyPasswordResetToken(PasswordResetToken passwordResetToken);

    void makeUsed(String token);

    void delete(String token);

    PasswordResetToken save(PasswordResetToken passwordResetToken);
}

package com.sam.blog_auth.repository;

import com.sam.blog_auth.entity.PasswordResetToken;
import com.sam.blog_user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByTokenValue(String token);
    Optional<PasswordResetToken> findByUser(User user);
}

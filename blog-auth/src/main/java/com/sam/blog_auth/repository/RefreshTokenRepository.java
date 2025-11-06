package com.sam.blog_auth.repository;

import com.sam.blog_auth.entity.RefreshToken;
import com.sam.blog_user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);
    List<RefreshToken> findAllByUserAndRevokedFalseAndExpiryDateAfter(User user, Instant instant);
}

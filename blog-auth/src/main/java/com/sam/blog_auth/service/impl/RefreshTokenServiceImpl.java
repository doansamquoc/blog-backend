package com.sam.blog_auth.service.impl;

import com.sam.blog_auth.entity.RefreshToken;
import com.sam.blog_auth.repository.RefreshTokenRepository;
import com.sam.blog_auth.service.RefreshTokenService;
import com.sam.blog_user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenServiceImpl implements RefreshTokenService {
    RefreshTokenRepository refreshTokenRepository;
    static long REFRESH_TOKEN_DURATION_MS = 7 * 24 * 60 * 60 * 1000L;

    @Override
    public RefreshToken generate(User user, HttpServletRequest request) {
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser(user);
        RefreshToken token = existingToken.orElseGet(RefreshToken::new);
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_DURATION_MS));
        token.setRevoked(false);
        token.setDeviceName(request.getHeader("User-Agent"));
        token.setIpAddress(request.getRemoteAddr());
        return refreshTokenRepository.save(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        return null;
    }
}

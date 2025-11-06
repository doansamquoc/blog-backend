package com.sam.blog_auth.service.impl;

import com.sam.blog_auth.entity.RefreshToken;
import com.sam.blog_auth.repository.RefreshTokenRepository;
import com.sam.blog_auth.service.RefreshTokenService;
import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.exception.BusinessException;
import com.sam.blog_user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenServiceImpl implements RefreshTokenService {
    RefreshTokenRepository repository;
    static long REFRESH_TOKEN_DURATION_MS = 7 * 24 * 60 * 60 * 1000L;

    /// Generate and save refresh token into database.
    /// Generate new refresh token
    @Override
    public RefreshToken generate(User user, HttpServletRequest request) {
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_DURATION_MS));
        token.setRevoked(false);
        token.setDeviceName(request.getHeader("User-Agent"));
        token.setIpAddress(request.getRemoteAddr());

        return repository.save(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        return null;
    }

    @Override
    public void revokeAndSave(String token) {
        repository.findByToken(token).ifPresent(refreshToken -> {
            refreshToken.setRevoked(true);
            repository.save(refreshToken);
        });
    }

    @Override
    public void revokeAndSave(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);
        repository.save(refreshToken);
    }

    @Override
    public void revokeAllByUser(User user) {
        List<RefreshToken> validTokens = repository.findAllByUserAndRevokedFalseAndExpiryDateAfter(user, Instant.now());
        if (validTokens.isEmpty())
            return;

        validTokens.forEach(token -> token.setRevoked(true));
        repository.saveAll(validTokens);
    }

    @Override
    public RefreshToken findByToken(String token) {
        return repository.findByToken(token).orElseThrow(() -> new BusinessException(ErrorCode.INVALID_TOKEN));
    }
}

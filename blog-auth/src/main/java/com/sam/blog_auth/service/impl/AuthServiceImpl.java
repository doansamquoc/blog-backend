package com.sam.blog_auth.service.impl;

import com.sam.blog_auth.dto.request.PasswordResetRequest;
import com.sam.blog_auth.dto.request.RequestRestPasswordRequest;
import com.sam.blog_auth.dto.request.SignInRequest;
import com.sam.blog_auth.dto.request.SignUpRequest;
import com.sam.blog_auth.dto.response.AuthResponse;
import com.sam.blog_auth.entity.PasswordResetToken;
import com.sam.blog_auth.entity.RefreshToken;
import com.sam.blog_auth.event.PasswordChangedEvent;
import com.sam.blog_auth.event.PasswordResetEvent;
import com.sam.blog_auth.mapper.AuthMapper;
import com.sam.blog_auth.service.AuthService;
import com.sam.blog_auth.service.PasswordResetTokenService;
import com.sam.blog_auth.service.RefreshTokenService;
import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.enums.Role;
import com.sam.blog_core.enums.TokenType;
import com.sam.blog_core.exception.BusinessException;
import com.sam.blog_core.service.JwtService;
import com.sam.blog_core.utils.CookieUtils;
import com.sam.blog_auth.dto.request.PasswordUpdateRequest;
import com.sam.blog_user.entity.User;
import com.sam.blog_user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    JwtService jwtService;
    AuthMapper authMapper;
    CookieUtils cookieUtils;
    UserService userService;
    PasswordEncoder passwordEncoder;
    PasswordResetTokenService passwordResetTokenService;
    RefreshTokenService refreshTokenService;
    AuthenticationManager authenticationManager;
    ApplicationEventPublisher eventPublisher;

    static int EXPIRATION_TIME_MS = 900_000;

    @Override
    public AuthResponse signUp(SignUpRequest request, HttpServletRequest servletRequest, HttpServletResponse response) {
        if (userService.existsByEmailAddress(request.getEmailAddress()))
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        if (userService.existsByUsername(request.getUsername()))
            throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS);

        User user = buildNewUser(request);
        userService.save(user);

        // Sign in to response access token and generate refresh token
        return signIn(new SignInRequest(request.getUsername(), request.getPassword()), servletRequest, response);
    }

    private User buildNewUser(SignUpRequest request) {
        User user = authMapper.toSIgnUpRequest(request);
        user.setRoles(Set.of(Role.USER));
        user.setHashedPassword(passwordEncoder.encode(request.getPassword()));
        user.setVerified(false);
        return user;
    }

    private User authenticate(SignInRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getIdentifier(), request.getPassword())
            );
            return (User) auth.getPrincipal();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }
    }

    public String generateAccessToken(User user) {
        Map<String, Object> claims = Map.of(
                "id", user.getId(),
                "roles", user.getRoles().stream().map(Enum::name).toList()
        );
        return jwtService.generate(claims, user.getUsername());
    }

    private void validateRefreshToken(RefreshToken refreshToken) {
        if (refreshToken.isRevoked())
            throw new BusinessException(ErrorCode.TOKEN_REVOKED);
        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenService.revokeAndSave(refreshToken);
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }
    }

    @Override
    public AuthResponse signIn(SignInRequest request, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        User user = authenticate(request);

        String accessToken = generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.generate(user, servletRequest);

        // Create cookie and add to header
        ResponseCookie refreshCookie = cookieUtils.createRefreshTokenCookie(refreshToken.getToken());
        cookieUtils.addCookieToHeader(servletResponse, refreshCookie);

        return AuthResponse.builder().accessToken(accessToken).tokenType(TokenType.BEARER.getName()).build();
    }

    private void ensureAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken)
            throw new BusinessException(ErrorCode.USER_NOT_LOGGED_IN);
    }

    @Override
    public void signOut(HttpServletRequest request, HttpServletResponse response) {
        // Check authenticate
        ensureAuthenticated();

        // Check refresh token in the request
        String refreshToken = cookieUtils.extractRefreshTokenFromRequest(request);
        if (refreshToken == null || refreshToken.isBlank())
            throw new BusinessException(ErrorCode.USER_NOT_LOGGED_IN);

        // Revoke refresh token in database
        refreshTokenService.revokeAndSave(refreshToken);

        // Clear refresh token in cookie
        ResponseCookie clearCookie = cookieUtils.deleteCookie("refreshToken", "/api/auth");
        cookieUtils.addCookieToHeader(response, clearCookie);

        // Clear context
        SecurityContextHolder.clearContext();
    }

    /// Refresh token rotation
    @Override
    public AuthResponse refresh(HttpServletRequest request, HttpServletResponse response) {
        // Get cookie from request
        Cookie cookie = cookieUtils.getCookie(request, "refreshToken")
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_TOKEN));

        // Get refresh token value and find it in database
        RefreshToken refreshToken = refreshTokenService.findByToken(cookie.getValue());
        validateRefreshToken(refreshToken);

        // Get user from old refresh token
        User user = refreshToken.getUser();

        // Rotate refresh token
        refreshTokenService.revokeAndSave(refreshToken);

        // Generate new access token
        String newAccessToken = generateAccessToken(user);
        // Generate new refresh token
        RefreshToken newRefreshToken = refreshTokenService.generate(user, request);

        // Set refresh token into cookie
        ResponseCookie refreshCookie = cookieUtils.createRefreshTokenCookie(newRefreshToken.getToken());
        cookieUtils.addCookieToHeader(response, refreshCookie);

        // Response new access token
        return AuthResponse.builder().accessToken(newAccessToken).tokenType(TokenType.BEARER.getName()).build();
    }

    private void savePasswordToken(User user, String tokenValue) {
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .tokenValue(tokenValue)
                .user(user)
                .expiryDate(Instant.now().plusMillis(EXPIRATION_TIME_MS))
                .isUsed(false)
                .build();
        passwordResetTokenService.save(passwordResetToken);
    }
    @Override
    public void updatePassword(PasswordUpdateRequest r, HttpServletRequest servletRequest) {
        User user = userService.authenticatedUser();

        // If password do not match throw an error
        if (!passwordEncoder.matches(r.getOldPassword(), user.getHashedPassword()))
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);

        // hashing new password and save to database
        String hashedNewPassword = passwordEncoder.encode(r.getNewPassword());
        user.setHashedPassword(hashedNewPassword);
        userService.save(user);

        // Revoke all sessions
        refreshTokenService.revokeAllByUser(user);

        // Initializing token
        String tokenValue = UUID.randomUUID().toString();
        savePasswordToken(user, tokenValue);

        publishPasswordChangedEvent(user, tokenValue, servletRequest);
    }

    @Override
    public void requestResetPassword(RequestRestPasswordRequest r, HttpServletRequest request) {
        User user = userService.findUserByEmail(r.getEmailAddress());

        String tokenValue = UUID.randomUUID().toString();
        savePasswordToken(user, tokenValue);

        PasswordResetEvent event = new PasswordResetEvent(this, user.getEmailAddress(), tokenValue, request);
        eventPublisher.publishEvent(event);
    }

    @Override
    public void resetPassword(String token, PasswordResetRequest request, HttpServletRequest servletRequest) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByTokenValue(token);
        passwordResetTokenService.verifyPasswordResetToken(passwordResetToken);
        User user = userService.findUserByUsername(passwordResetToken.getUser().getUsername());

        String hashedNewPassword = passwordEncoder.encode(request.getNewPassword());
        user.setHashedPassword(hashedNewPassword);
        userService.save(user);
        passwordResetTokenService.makeUsed(token);

        // Revoke all sessions
        refreshTokenService.revokeAllByUser(user);

        // Generate new token for reset password if it doesn't user did
        String tokenValue = UUID.randomUUID().toString();
        savePasswordToken(user, tokenValue);

        publishPasswordChangedEvent(user, tokenValue, servletRequest);
    }

    private void publishPasswordChangedEvent(User user, String tokenValue, HttpServletRequest servletRequest) {
        PasswordChangedEvent event = new PasswordChangedEvent(this, user.getEmailAddress(), tokenValue, servletRequest);
        eventPublisher.publishEvent(event);
    }
}

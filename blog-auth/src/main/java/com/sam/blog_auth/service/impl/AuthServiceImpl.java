package com.sam.blog_auth.service.impl;

import com.sam.blog_auth.dto.request.SignInRequest;
import com.sam.blog_auth.dto.request.SignUpRequest;
import com.sam.blog_auth.dto.response.AuthResponse;
import com.sam.blog_auth.entity.RefreshToken;
import com.sam.blog_auth.mapper.AuthMapper;
import com.sam.blog_auth.service.AuthService;
import com.sam.blog_auth.service.RefreshTokenService;
import com.sam.blog_core.enums.TokenType;
import com.sam.blog_core.service.JwtService;
import com.sam.blog_core.utils.CookieUtils;
import com.sam.blog_user.entity.User;
import com.sam.blog_user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    JwtService jwtService;
    AuthMapper authMapper;
    CookieUtils cookieUtils;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RefreshTokenService refreshTokenService;
    AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signUp(SignUpRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public AuthResponse signIn(SignInRequest r, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                r.getIdentifier(), r.getPassword()
        );

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        User user = (User) authenticate.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("roles", user.getRoles().stream().map(Enum::name).toList());

        String accessToken = jwtService.generate(claims, user.getUsername());
        RefreshToken refreshToken = refreshTokenService.generate(user, request);

        // Create cookie
        ResponseCookie refreshCookie = cookieUtils.createRefreshTokenCookie(refreshToken.getToken());

        // Add cookie to header
        cookieUtils.addCookieToHeader(response, refreshCookie);

        return AuthResponse.builder().accessToken(accessToken).tokenType(TokenType.BEARER).build();
    }
}

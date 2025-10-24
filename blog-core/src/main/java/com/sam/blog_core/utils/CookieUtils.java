package com.sam.blog_core.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CookieUtils {

    public ResponseCookie createCookie(String name, String value, long maxAge, String path) {
        return ResponseCookie.from(name, value)
                .httpOnly(false)
                .secure(true)
                .path(path)
                .sameSite("None")
                .maxAge(maxAge)
                .build();
    }

    // Expiration divided by 1000 because JWT expiration usually takes milliseconds but Cookies do not
    public ResponseCookie createAccessTokenCookie(String value) {
        // 15 minutes
        long accessTokenExpiration = 15 * 60 * 1000;
        return createCookie("accessToken", value, accessTokenExpiration / 1000, "/");
    }

    // Expiration divided by 1000 because JWT expiration usually takes milliseconds but Cookies do not
    public ResponseCookie createRefreshTokenCookie(String value) {
        // 15 days
        long refreshTokenExpiration = 15 * 24 * 60 * 60 * 1000;
        return createCookie("refreshToken", value, refreshTokenExpiration / 1000, "/api/auth/refresh");
    }

    public ResponseCookie deleteCookie(String name, String path) {
        return ResponseCookie.from(name)
                .httpOnly(false)
                .secure(true)
                .path(path)
                .sameSite("None")
                .maxAge(0)
                .build();
    }

    public void addCookieToHeader(HttpServletResponse response, ResponseCookie cookie) {
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(name)).findFirst();
        }
        return Optional.empty();
    }
}
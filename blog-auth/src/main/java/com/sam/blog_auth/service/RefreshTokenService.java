package com.sam.blog_auth.service;

import com.sam.blog_auth.entity.RefreshToken;
import com.sam.blog_user.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface RefreshTokenService {
    RefreshToken generate(User user, HttpServletRequest request);
    RefreshToken verifyExpiration(RefreshToken token);
    void revoke(String token);
    void revokeAllByUser(User user);
}

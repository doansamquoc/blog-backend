package com.sam.blog_core.service;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtService {
    String generate(Map<String, Object> claims, String subject);
    String extractUsername(String token);
    Claims parse(String token);
    boolean validate(String token, String subject);
}


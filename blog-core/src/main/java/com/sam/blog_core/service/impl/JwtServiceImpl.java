package com.sam.blog_core.service.impl;

import com.sam.blog_core.enums.Role;
import com.sam.blog_core.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtServiceImpl implements JwtService {
    String SECRET_KEY = "this_is_secret_key_too_long_long_long";
    SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    @Override
    public String generate(Map<String, Object> claims, String subject) {
        Date now = new Date();
        long EXPIRATION = 15 * 60 * 1000; // 15 minutes
        Date expiration = new Date(System.currentTimeMillis() + EXPIRATION);
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(KEY)
                .compact();
    }

    @Override
    public Claims parse(String token) {
        return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token).getPayload();
    }

    @Override
    public String extractUsername(String token) {
        return parse(token).getSubject();
    }


    @Override
    public boolean validate(String token, String subject) {
        try {
            Claims claims = parse(token);
            String username = claims.getSubject();
            Date expiration = claims.getExpiration();
            boolean expired = expiration.before(new Date());

            return username.equals(subject) && !expired;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT: {}", e.getMessage());
            return false;
        }
    }
}

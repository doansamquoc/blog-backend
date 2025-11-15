package com.sam.blog_core.utils;

import org.springframework.security.oauth2.jwt.Jwt;

public class JWTUtils {
    public static long getLongClaim(Jwt jwt, String key) {
        return ((Number) jwt.getClaims().get(key)).longValue();
    }

    public static String getStringClaim(Jwt jwt, String key) {
        return jwt.getClaims().get(key).toString();
    }

    public static int getIntClaim(Jwt jwt, String key) {
        return ((Number) jwt.getClaims().get(key)).intValue();
    }
}

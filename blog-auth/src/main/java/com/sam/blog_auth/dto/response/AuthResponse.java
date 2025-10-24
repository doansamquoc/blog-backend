package com.sam.blog_auth.dto.response;

import com.sam.blog_core.enums.TokenType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AuthResponse {
    String accessToken;
    TokenType tokenType;
}

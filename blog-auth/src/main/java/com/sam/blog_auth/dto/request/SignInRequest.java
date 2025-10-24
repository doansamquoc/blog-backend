package com.sam.blog_auth.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
// Can use email or username to sign in
public class SignInRequest {
    String identifier;
    String password;
}

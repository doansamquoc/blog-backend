package com.sam.blog_user.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckUsernameRequest {
    @Size(min = 3, max = 12, message = "INVALID_USERNAME")
    String username;
}

package com.sam.blog_user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sam.blog_core.enums.Gender;
import com.sam.blog_core.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    long id;
    String username;
    String firstName;
    String lastName;
    LocalDateTime dateOfBirth;
    Gender gender;
    String emailAddress;
    Set<Role> roles;
    Instant createdAt;
    Instant updatedAt;
}

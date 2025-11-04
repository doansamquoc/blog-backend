package com.sam.blog_user.dto.request;

import com.sam.blog_core.enums.Gender;
import com.sam.blog_core.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String username;
    String firstName;
    String lastName;
    String avatarUrl;
    boolean isVerified;
    LocalDate dateOfBirth;
    Gender gender;
    String emailAddress;
    Set<Role> roles;
}

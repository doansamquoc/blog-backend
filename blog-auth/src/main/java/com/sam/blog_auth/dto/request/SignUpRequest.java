package com.sam.blog_auth.dto.request;

import com.sam.blog_core.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
/*
 * The error message must be contained the num key defined in ErrorCode
 */
public class SignUpRequest {
    @NotBlank(message = "EMAIL_ADDRESS_REQUIRED")
    @Email(message = "INVALID_EMAIL_ADDRESS")
    String emailAddress;

    @NotBlank(message = "PASSWORD_REQUIRED")
    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

    @NotBlank(message = "USERNAME_REQUIRED")
    @Size(min = 4, max = 16, message = "INVALID_USERNAME")
    String username;

    @NotBlank(message = "FIRST_NAME_REQUIRED")
    @Size(min = 4, max = 54, message = "INVALID_FIRST_NAME")
    String firstName;

    @Size(min = 4, max = 54, message = "INVALID_LAST_NAME")
    String lastName;

    @NotNull(message = "DOB_REQUIRED")
    @Past(message = "DOB_MUST_BE_IN_THE_PAST")
    LocalDate dateOfBirth;

    @NotNull(message = "INVALID_GENDER")
    Gender gender;
}

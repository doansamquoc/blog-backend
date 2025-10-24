package com.sam.blog_user.dto.request;

import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_user.enums.Gender;
import com.sam.blog_user.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
/*
 * The error message must be contained the num key defined in ErrorCode
 */
public class UserCreationRequest {
    @NotBlank(message = "INVALID_EMAIL_ADDRESS")
    @Email(message = "INVALID_EMAIL_ADDRESS")
    String emailAddress;

    @NotBlank(message = "INVALID_PASSWORD")
    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

    @NotBlank(message = "INVALID_USERNAME")
    @Size(min = 4, max = 16, message = "INVALID_USERNAME")
    String username;

    @NotBlank(message = "INVALID_FIRSTNAME")
    @Size(min = 4, max = 54, message = "INVALID_FIRSTNAME")
    String firstName;

    @NotBlank(message = "INVALID_LASTNAME")
    @Size(min = 4, max = 54, message = "INVALID_LASTNAME")
    String lastName;

    @NotNull(message = "DATE_OF_BIRTH_REQUIRED")
    @Past(message = "DATE_OF_BIRTH_MUST_BE_IN_THE_PAST")
    LocalDateTime dateOfBirth;

    @NotNull(message = "INVALID_GENDER")
    Gender gender;
}

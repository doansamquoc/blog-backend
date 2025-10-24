package com.sam.blog_user.entity;

import com.sam.blog_core.entity.BaseEntity;
import com.sam.blog_user.enums.Gender;
import com.sam.blog_user.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Column(name = "username", unique = true, nullable = false)
    String username;

    @Column(name = "first_name", nullable = false, length = 32)
    String firstName;

    @Column(name="last_name", length = 32)
    String lastName;

    @Column(name = "hashed_password", nullable = false)
    String hashedPassword;

    @Column(name = "date_of_birth")
    LocalDateTime dateOfBirth;

    @Column(name = "gender", length = 6)
    Gender gender;

    @Column(name = "email_address", unique = true, nullable = false)
    String emailAddress;

    @Column(name = "roles", length = 6)
    Set<Role> roles;
}

package com.sam.blog_user.entity;

import com.sam.blog_core.entity.BaseEntity;
import com.sam.blog_core.enums.Gender;
import com.sam.blog_core.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {
    @Column(name = "username", unique = true, nullable = false)
    String username;

    @Column(name = "first_name", nullable = false, length = 32)
    String firstName;

    @Column(name = "last_name", length = 32)
    String lastName;

    @Column(name = "hashed_password", nullable = false)
    String hashedPassword;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    @Column(name = "gender", length = 6)
    Gender gender;

    @Column(name = "email_address", unique = true, nullable = false)
    String emailAddress;

    @Column(name = "roles", length = 6)
    Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles.isEmpty()) return Collections.emptySet();
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).toList();
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

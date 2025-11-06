package com.sam.blog_auth.entity;

import com.sam.blog_core.entity.BaseEntity;
import com.sam.blog_user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Table(name = "password_reset_tokens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordResetToken extends BaseEntity {
    @Column(name = "token_value", unique = true, nullable = false)
    String tokenValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "expiry_date", nullable = false)
    Instant expiryDate;

    @Column(name = "is_used", nullable = false)
    boolean isUsed;
}

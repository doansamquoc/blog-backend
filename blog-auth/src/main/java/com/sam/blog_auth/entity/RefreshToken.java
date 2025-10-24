package com.sam.blog_auth.entity;

import com.sam.blog_core.entity.BaseEntity;
import com.sam.blog_user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RefreshToken extends BaseEntity {

    @Column(name = "token", nullable = false, unique = true, length = 512)
    String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "expiry_date", nullable = false)
    Instant expiryDate;

    @Column(name = "device_name", nullable = false)
    String deviceName;

    @Column(name = "ip_address", nullable = false)
    String ipAddress;

    @Column(name = "revoked", nullable = false)
    boolean revoked;
}

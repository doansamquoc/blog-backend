package com.sam.like_impl.entity;

import com.sam.blog_core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "likes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Like extends BaseEntity {
    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "post_id", nullable = false)
    Long postId;
}

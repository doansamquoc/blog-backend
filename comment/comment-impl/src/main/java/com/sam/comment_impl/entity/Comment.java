package com.sam.comment_impl.entity;

import com.sam.blog_core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends BaseEntity {
    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "post_id", nullable = false)
    Long postId;

    @Column(name = "content")
    String content;
}

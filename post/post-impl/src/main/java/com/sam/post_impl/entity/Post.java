package com.sam.post_impl.entity;

import com.sam.blog_core.entity.BaseEntity;
import com.sam.blog_user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Table(name = "posts")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post extends BaseEntity {
    @Column(name = "author_id", nullable = false)
    Long authorId;

    @Column(name = "caption")
    String caption;
}

package com.sam.blog_post.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.User;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    Long id;
    Long creatorId;
    String caption;
    Instant createdAt;
    Instant updatedAt;
}

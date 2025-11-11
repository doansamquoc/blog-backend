package com.sam.blog_comment.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    Long id;
    Long commenterId;
    Long postId;
    String content;
    Instant createdAt;
    Instant updatedAt;
}

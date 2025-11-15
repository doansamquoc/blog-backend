package com.sam.like_impl.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LikeResponse {
    Long id;
    Long userId;
    Long postId;
    Instant createdAt;
    Instant updatedAt;
}

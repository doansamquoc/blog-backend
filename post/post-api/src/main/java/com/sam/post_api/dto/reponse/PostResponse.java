package com.sam.post_api.dto.reponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    Long id;
    Long authorId;
    String caption;
    Instant createdAt;
    Instant updatedAt;
}

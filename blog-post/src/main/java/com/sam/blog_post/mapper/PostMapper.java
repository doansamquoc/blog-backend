package com.sam.blog_post.mapper;

import com.sam.blog_post.dto.request.PostCreationRequest;
import com.sam.blog_post.dto.request.PostUpdateRequest;
import com.sam.blog_post.dto.response.PostResponse;
import com.sam.blog_post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {
    @Mapping(target = "authorId", source = "author.id")
    PostResponse toPostResponse(Post post);
    Post createPostFromRequest(PostCreationRequest request);
    Post updatePostFromRequest(PostUpdateRequest request, @MappingTarget Post post);
}

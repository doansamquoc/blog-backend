package com.sam.post_impl.mapper;

import com.sam.post_api.dto.PostDTO;
import com.sam.post_api.dto.reponse.PostResponse;
import com.sam.post_api.dto.request.PostCreationRequest;
import com.sam.post_api.dto.request.PostUpdateRequest;
import com.sam.post_impl.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {
    PostDTO toPostDTO(Post post);

    PostResponse toPostResponse(Post post);

    Post createPostFromRequest(PostCreationRequest request);

    Post updatePostFromRequest(PostUpdateRequest request, @MappingTarget Post post);
}
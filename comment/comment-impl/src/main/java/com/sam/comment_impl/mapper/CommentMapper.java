package com.sam.comment_impl.mapper;

import com.sam.comment_api.dto.request.CommentCreationRequest;
import com.sam.comment_api.dto.request.CommentUpdateRequest;
import com.sam.comment_api.dto.response.CommentResponse;
import com.sam.comment_impl.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {
    CommentResponse toCommentResponse(Comment comment);

    Comment createCommentFromRequest(CommentCreationRequest request);

    Comment updateCommentFromRequest(CommentUpdateRequest request, @MappingTarget Comment comment);
}

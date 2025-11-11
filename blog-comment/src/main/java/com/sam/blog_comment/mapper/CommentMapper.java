package com.sam.blog_comment.mapper;

import com.sam.blog_comment.dto.request.CommentCreationRequest;
import com.sam.blog_comment.dto.request.CommentUpdateRequest;
import com.sam.blog_comment.dto.response.CommentResponse;
import com.sam.blog_comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {
    @Mapping(target = "commenterId", source = "commenter.id")
    @Mapping(target = "postId", source = "post.id")
    CommentResponse toCommentResponse(Comment comment);

    @Mapping(target = "commenter", ignore = true)
    @Mapping(target = "post", ignore = true)
    Comment createCommentFromRequest(CommentCreationRequest request);

    @Mapping(target = "commenter", ignore = true)
    @Mapping(target = "post", ignore = true)
    Comment updateCommentFromRequest(CommentUpdateRequest request, @MappingTarget Comment comment);
}

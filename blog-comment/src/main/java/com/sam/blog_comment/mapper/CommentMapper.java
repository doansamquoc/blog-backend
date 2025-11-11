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
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "postId", source = "post.id")
    CommentResponse toCommentResponse(Comment comment);

    Comment createCommentFromRequest(CommentCreationRequest request);

    Comment updateCommentFromRequest(CommentUpdateRequest request, @MappingTarget Comment comment);
}

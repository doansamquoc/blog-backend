package com.sam.blog_user.mapper;

import com.sam.blog_user.dto.response.UserResponse;
import com.sam.blog_user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserResponse toUserResponse(User user);
}

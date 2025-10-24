package com.sam.blog_auth.mapper;

import com.sam.blog_auth.dto.request.SignInRequest;
import com.sam.blog_auth.dto.request.SignUpRequest;
import com.sam.blog_auth.dto.response.AuthResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthMapper {
    AuthResponse toSignInRequest(SignInRequest request);
    AuthResponse toSIgnUpRequest(SignUpRequest request);
    AuthResponse toAuthResponse(AuthResponse response);
}

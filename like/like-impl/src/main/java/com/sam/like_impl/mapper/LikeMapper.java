package com.sam.like_impl.mapper;

import com.sam.like_impl.dto.request.LikeCreationRequest;
import com.sam.like_impl.dto.response.LikeResponse;
import com.sam.like_impl.entity.Like;
import com.sam.like_api.dto.LikeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LikeMapper {
    LikeResponse toLikeResponse(Like like);

    LikeDTO toLikeDTOResponse(Like like);

    Like createLikeFromRequest(LikeCreationRequest request);
}

package com.sam.like_impl.impl;

import com.sam.blog_core.dto.response.PageResponse;
import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.exception.BusinessException;
import com.sam.like_impl.dto.request.LikeCreationRequest;
import com.sam.like_impl.dto.response.LikeResponse;
import com.sam.like_impl.entity.Like;
import com.sam.like_impl.mapper.LikeMapper;
import com.sam.like_impl.repository.LikeRepository;
import com.sam.like_impl.service.LikeService;
import com.sam.like_api.dto.LikeDTO;
import com.sam.post_api.dto.PostDTO;
import com.sam.post_api.service.PostService;
import com.sam.blog_user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LikeServiceImpl implements LikeService {
    LikeRepository repository;
    LikeMapper mapper;
    UserService userService;
    PostService postService;

    @Override
    public LikeResponse create(Long userId, LikeCreationRequest request) {
        Like like = mapper.createLikeFromRequest(request);
        like.setUserId(userId);
        return mapper.toLikeResponse(repository.save(like));
    }

    @Override
    public void delete(Long userId, Long postId) {
        Like like = findLikeEntity(userId, postId);
        repository.delete(like);
    }

    @Override
    public PageResponse<LikeResponse> getAll(Long postId, int page, int size) {
        PostDTO postDTO = postService.findPostDTOById(postId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("CreatedAt").descending());
        Page<Like> likes = repository.findByPostId(postDTO.getId(), pageable);
        List<LikeResponse> likeResponses = likes.stream().map(mapper::toLikeResponse).toList();
        return PageResponse.of(likeResponses, likes);
    }

    @Override
    public LikeDTO findLikeDTO(Long userId, Long postId) {
        Like like = repository.findByUserIdAndPostId(userId, postId).orElseThrow(
                () -> new BusinessException(ErrorCode.LIKE_NOT_FOUND)
        );
        return mapper.toLikeDTOResponse(like);
    }

    private Like findLikeEntity(Long userId, Long postId) {
        return repository.findByUserIdAndPostId(userId, postId).orElseThrow(
                () -> new BusinessException(ErrorCode.LIKE_NOT_FOUND)
        );
    }

    @Override
    public Map<Long, Long> countByPostIds(List<Long> postIds) {
        return repository.countByPostIds(postIds).stream()
                .collect(Collectors.toMap(
                        row -> (Long) row.get("postId"),
                        row -> (Long) row.get("likeCount"))
                );
    }
}

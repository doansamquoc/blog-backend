package com.sam.post_impl.impl;

import com.sam.blog_core.dto.response.PageResponse;
import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.exception.BusinessException;
import com.sam.post_api.dto.PostDTO;
import com.sam.post_api.dto.reponse.PostResponse;
import com.sam.post_api.dto.request.PostCreationRequest;
import com.sam.post_api.dto.request.PostUpdateRequest;
import com.sam.post_impl.mapper.PostMapper;
import com.sam.post_api.service.PostService;
import com.sam.post_impl.entity.Post;
import com.sam.post_impl.repository.PostRepository;
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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostServiceImpl implements PostService {
    PostRepository repository;
    PostMapper mapper;
    UserService userService;

    @Override
    public PostResponse create(Long userId, PostCreationRequest request) {
        Post post = mapper.createPostFromRequest(request);
        post.setAuthorId(userId);
        Post postSaved = repository.save(post);

        return mapper.toPostResponse(postSaved);
    }

    @Override
    public PageResponse<PostResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> posts = repository.findAll(pageable);
        List<PostResponse> content = posts.getContent().stream().map(mapper::toPostResponse).toList();
        return PageResponse.of(content, posts);
    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = findPostEntityById(id);
        return mapper.toPostResponse(post);
    }

    @Override
    public PostDTO findPostDTOById(Long id) {
        return mapper.toPostDTO(findPostEntityById(id));
    }

    @Override
    public void deleteByIdAndAuthorId(Long postId, Long authorId) {
        Post post = findPostEntityByIdAndAuthorId(postId, authorId);
        repository.delete(post);
    }

    @Override
    public PostResponse updateByIdAndAuthorId(Long postId, Long authorId, PostUpdateRequest request) {
        Post post = findPostEntityByIdAndAuthorId(postId, authorId);
        post = mapper.updatePostFromRequest(request, post);
        Post postUpdated = repository.save(post);

        return mapper.toPostResponse(postUpdated);
    }

    private Post findPostEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
    }

    private Post findPostEntityByIdAndAuthorId(Long postId, Long authorId) {
        return repository.findByIdAndAuthorId(postId, authorId).orElseThrow(
                () -> new BusinessException(ErrorCode.POST_NOT_FOUND)
        );
    }
}

package com.sam.blog_post.service.impl;

import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.exception.BusinessException;
import com.sam.blog_post.dto.request.PostCreationRequest;
import com.sam.blog_post.dto.request.PostUpdateRequest;
import com.sam.blog_user.entity.User;
import com.sam.blog_post.dto.response.PageResponse;
import com.sam.blog_post.dto.response.PostResponse;
import com.sam.blog_post.entity.Post;
import com.sam.blog_post.mapper.PostMapper;
import com.sam.blog_post.repository.PostRepository;
import com.sam.blog_post.service.PostService;
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
    public PostResponse create(String username, PostCreationRequest request) {
        User user = userService.findUserByUsername(username);
        Post post = mapper.createPostFromRequest(request);
        post.setCreator(user);
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
    public PostResponse getById(Long id) {
        Post post = findById(id);
        return mapper.toPostResponse(post);
    }

    @Override
    public void deleteById(String username, Long id) {
        Post post = findByIdAndUsername(id, username);
        repository.delete(post);
    }

    @Override
    public PostResponse updateByIdAndUsername(Long id, String username, PostUpdateRequest request) {
        Post post = findByIdAndUsername(id, username);
        post = mapper.updatePostFromRequest(request, post);
        Post postUpdated = repository.save(post);

        return mapper.toPostResponse(postUpdated);
    }

    public Post findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
    }

    public Post findByIdAndUsername(Long id, String username) {
        User user = userService.findUserByUsername(username);
        return repository.findByIdAndCreator(id, user).orElseThrow(
                () -> new BusinessException(ErrorCode.POST_NOT_FOUND)
        );
    }
}

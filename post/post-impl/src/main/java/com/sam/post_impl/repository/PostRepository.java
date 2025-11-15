package com.sam.post_impl.repository;

import com.sam.post_impl.entity.Post;
import com.sam.blog_user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorId(Long id);
    Optional<Post> findByIdAndAuthorId(Long postId, Long authorId);
}

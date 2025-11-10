package com.sam.blog_post.repository;

import com.sam.blog_post.entity.Post;
import com.sam.blog_user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCreator(User user);
    Optional<Post> findByIdAndCreator(Long id, User user);
}

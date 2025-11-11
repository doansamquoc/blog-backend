package com.sam.blog_comment.repository;

import com.sam.blog_comment.entity.Comment;
import com.sam.blog_post.entity.Post;
import com.sam.blog_user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPost(Post post, Pageable pageable);

    Optional<Comment> findByIdAndCommenter(Long id, User user);
}

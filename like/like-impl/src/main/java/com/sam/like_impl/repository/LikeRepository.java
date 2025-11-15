package com.sam.like_impl.repository;

import com.sam.like_impl.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    @Query("SELECT l.postId AS postId, COUNT(1) AS likeCount " +
            "FROM Like l " +
            "WHERE l.postId IN :postIds " +
            "GROUP BY l.postId"
    )
    List<Map<String, Object>> countByPostIds(@Param("postIds") List<Long> postIds);

    Page<Like> findByPostId(Long id, Pageable pageable);
}

package com.example.sixsevenchan.repository;

import com.example.sixsevenchan.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    interface PostSummaryProjection {
        Long getId();
        String getText();
        LocalDateTime getCreatedAt();
        Long getThreadId();
    }

    @Query(value = """
    SELECT id, text, created_at AS createdAt, thread_id AS threadId
    FROM (
        SELECT p.id, p.text, p.created_at, p.thread_id, p.is_op,
               ROW_NUMBER() OVER(PARTITION BY p.thread_id ORDER BY p.created_at DESC) as rn
        FROM post p
        WHERE p.thread_id IN (:threadIds)
    ) ranked_posts
    WHERE ranked_posts.rn <= 3 OR ranked_posts.is_op = true
    ORDER BY ranked_posts.thread_id, ranked_posts.created_at ASC
    """, nativeQuery = true)
    List<PostSummaryProjection> getTopPostsForThreads(@Param("threadIds") List<Long> threadIds);

    List<Post> getPostByThreadId(Long threadId);
}

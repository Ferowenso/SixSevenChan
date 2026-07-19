package com.example.sixsevenchan.repository;

import com.example.sixsevenchan.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getPostByThreadId(Long threadId);
}

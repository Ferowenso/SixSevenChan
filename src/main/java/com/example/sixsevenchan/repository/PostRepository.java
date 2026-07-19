package com.example.sixsevenchan.repository;

import com.example.sixsevenchan.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> { }

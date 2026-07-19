package com.example.sixsevenchan.repository;

import com.example.sixsevenchan.entity.Board;
import com.example.sixsevenchan.entity.Thread;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {

    @EntityGraph(attributePaths = "posts")
    List<Thread> findThreadsByBoard(Board board);
}

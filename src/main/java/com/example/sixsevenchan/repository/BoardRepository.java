package com.example.sixsevenchan.repository;

import com.example.sixsevenchan.entity.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    boolean existsByPrefix(String prefix);

    Optional<Board> findByPrefix(String prefix);
}

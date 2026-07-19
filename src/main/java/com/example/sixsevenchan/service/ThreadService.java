package com.example.sixsevenchan.service;

import com.example.sixsevenchan.dto.request.ThreadCreateRequest;
import com.example.sixsevenchan.dto.response.PostResponse;
import com.example.sixsevenchan.dto.response.ThreadResponse;
import com.example.sixsevenchan.entity.Board;
import com.example.sixsevenchan.entity.Post;
import com.example.sixsevenchan.entity.Thread;
import com.example.sixsevenchan.repository.BoardRepository;
import com.example.sixsevenchan.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThreadService {

    private final ThreadRepository threadRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public ThreadResponse save(String prefix, ThreadCreateRequest request){

        Board board = boardRepository.findByPrefix(prefix).orElseThrow(() -> new IllegalArgumentException("Доски с префиксом /" +prefix +" не существует"));

        Thread thread = new Thread();
        thread.setSubject(request.getSubject());
        thread.setBoard(board);

        Post post = new Post();
        post.setThread(thread);
        post.setText(request.getText());

        thread.getPosts().add(post);

        Thread savedThread = threadRepository.save(thread);
        return new ThreadResponse(
                savedThread.getId(),
                savedThread.getSubject(),
                savedThread.isPinned(),
                savedThread.isClosed(),
                savedThread.getCreatedAt(),
                savedThread.getUpdatedAt(),
                savedThread.getPosts()
                        .stream()
                        .map(p -> new PostResponse(p.getId(), p.getText(), p.getCreatedAt()))
                        .toList());
    }

    @Transactional(readOnly = true)
    public List<ThreadResponse> findAllByBoard(String prefix){
        Board board = boardRepository.findByPrefix(prefix).orElseThrow(() -> new IllegalArgumentException("Доски с префиксом /" +prefix +" не существует"));
        List<Thread> threads = threadRepository.findThreadsByBoard(board);
        return threads.stream().map(t -> new ThreadResponse(
                t.getId(),
                t.getSubject(),
                t.isPinned(),
                t.isClosed(),
                t.getCreatedAt(),
                t.getUpdatedAt(),
                t.getPosts().stream()
                        .map(p -> new PostResponse(p.getId(), p.getText(), p.getCreatedAt()))
                        .toList())).toList();

    }

}

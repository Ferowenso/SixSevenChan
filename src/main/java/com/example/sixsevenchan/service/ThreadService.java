package com.example.sixsevenchan.service;

import com.example.sixsevenchan.dto.request.ThreadCreateRequest;
import com.example.sixsevenchan.dto.response.PostResponse;
import com.example.sixsevenchan.dto.response.ThreadResponse;
import com.example.sixsevenchan.entity.Board;
import com.example.sixsevenchan.entity.Post;
import com.example.sixsevenchan.entity.Thread;
import com.example.sixsevenchan.exception.ResourceNotFoundException;
import com.example.sixsevenchan.repository.BoardRepository;
import com.example.sixsevenchan.repository.PostRepository;
import com.example.sixsevenchan.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThreadService {

    private final ThreadRepository threadRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    @Transactional
    public ThreadResponse save(String prefix, ThreadCreateRequest request){

        Board board = boardRepository.findByPrefix(prefix).orElseThrow(() -> new ResourceNotFoundException("Доски с префиксом /" +prefix +" не существует"));

        Thread thread = new Thread();
        thread.setSubject(request.getSubject());
        thread.setBoard(board);
        thread.setBumpedAt(LocalDateTime.now());

        Post post = new Post();
        post.setThread(thread);
        post.setText(request.getText());
        post.setOp(true);

        thread.getPosts().add(post);

        Thread savedThread = threadRepository.save(thread);
        return new ThreadResponse(
                savedThread.getId(),
                savedThread.getSubject(),
                savedThread.isPinned(),
                savedThread.isClosed(),
                savedThread.getCreatedAt(),
                savedThread.getUpdatedAt(),
                savedThread.getBumpedAt(),
                savedThread.getPosts()
                        .stream()
                        .map(p -> new PostResponse(p.getId(), p.getText(), p.getCreatedAt(), p.isSage()))
                        .toList());
    }

    @Transactional(readOnly = true)
    public Page<ThreadResponse> findAllByBoard(String prefix, Pageable pageable){
        Board board = boardRepository.findByPrefix(prefix).orElseThrow(() -> new ResourceNotFoundException("Доски с префиксом /" +prefix +" не существует"));
        Page<Thread> threads = threadRepository.findThreadsByBoard(board, pageable);

        if (threads.isEmpty()) return Page.empty(pageable);

        List<Long> threadIds = threads.getContent().stream().map(Thread::getId).toList();

        List<PostRepository.PostSummaryProjection> recentPosts = postRepository.getTopPostsForThreads(threadIds);

        Map<Long, List<PostResponse>> postsByThreadId = recentPosts.stream()
                .collect(Collectors.groupingBy(
                        PostRepository.PostSummaryProjection::getThreadId,
                        Collectors.mapping(
                                p -> new PostResponse(p.getId(), p.getText(), p.getCreatedAt(), p.isSage()),
                                Collectors.toList()
                        )
                ));


        return threads.map(t -> new ThreadResponse(
                t.getId(),
                t.getSubject(),
                t.isPinned(),
                t.isClosed(),
                t.getCreatedAt(),
                t.getUpdatedAt(),
                t.getBumpedAt(),
                postsByThreadId.getOrDefault(t.getId(), List.of())));

    }

}

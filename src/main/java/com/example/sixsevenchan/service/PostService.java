package com.example.sixsevenchan.service;

import com.example.sixsevenchan.dto.request.PostCreateRequest;
import com.example.sixsevenchan.dto.response.PostResponse;
import com.example.sixsevenchan.entity.Post;
import com.example.sixsevenchan.entity.Thread;
import com.example.sixsevenchan.exception.ResourceNotFoundException;
import com.example.sixsevenchan.repository.PostRepository;
import com.example.sixsevenchan.repository.ThreadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ThreadRepository threadRepository;

    @Transactional(readOnly = true)
    public List<PostResponse> findAllByThread(Long threadId){

        if (!threadRepository.existsById(threadId)) {
            throw new ResourceNotFoundException("Треда с айди " + threadId + " не существует");
        }

        List<Post> posts = postRepository.getPostByThreadId(threadId);
        return posts.stream()
                .map(p -> new PostResponse(
                        p.getId(),
                        p.getText(),
                        p.getCreatedAt())).toList();
    }

    @Transactional
    public PostResponse save(Long threadId,PostCreateRequest request){
        Thread thread = threadRepository.findById(threadId).orElseThrow(() -> new ResourceNotFoundException("Треда с айди " + threadId + " не существует"));

        Post post = new Post();
        post.setText(request.getText());
        post.setThread(thread);

        Post savedPost = postRepository.save(post);

        thread.setUpdatedAt(LocalDateTime.now());

        return new PostResponse(
                savedPost.getId(),
                savedPost.getText(),
                savedPost.getCreatedAt());

    }



}

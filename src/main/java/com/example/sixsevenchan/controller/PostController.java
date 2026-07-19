package com.example.sixsevenchan.controller;

import com.example.sixsevenchan.dto.request.PostCreateRequest;
import com.example.sixsevenchan.dto.response.PostResponse;
import com.example.sixsevenchan.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/threads/{threadId}/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostResponse> findAllByThread(@PathVariable Long threadId){
        return postService.findAllByThread(threadId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse save(@PathVariable Long threadId, @Valid @RequestBody PostCreateRequest request){
        return postService.save(threadId, request);
    }

}

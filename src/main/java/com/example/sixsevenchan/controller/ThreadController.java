package com.example.sixsevenchan.controller;

import com.example.sixsevenchan.dto.request.ThreadCreateRequest;
import com.example.sixsevenchan.dto.response.ThreadResponse;
import com.example.sixsevenchan.service.ThreadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards/{prefix}/threads")
@RequiredArgsConstructor
public class ThreadController {

    private final ThreadService threadService;

    @GetMapping
    public List<ThreadResponse> findAllByBoard(@PathVariable String prefix){
        return threadService.findAllByBoard(prefix);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ThreadResponse save(@PathVariable String prefix, @Valid @RequestBody ThreadCreateRequest request){
        return threadService.save(prefix, request);
    }

}

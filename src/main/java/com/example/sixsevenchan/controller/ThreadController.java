package com.example.sixsevenchan.controller;

import com.example.sixsevenchan.dto.request.ThreadCreateRequest;
import com.example.sixsevenchan.dto.response.ThreadResponse;
import com.example.sixsevenchan.service.ThreadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards/{prefix}/threads")
@RequiredArgsConstructor
public class ThreadController {

    private final ThreadService threadService;

    @GetMapping
    public Page<ThreadResponse> findAllByBoard(@PathVariable String prefix, @PageableDefault(size = 15, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable){
        return threadService.findAllByBoard(prefix, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ThreadResponse save(@PathVariable String prefix, @Valid @RequestBody ThreadCreateRequest request){
        return threadService.save(prefix, request);
    }

}

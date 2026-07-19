package com.example.sixsevenchan.controller;

import com.example.sixsevenchan.dto.request.BoardCreateRequest;
import com.example.sixsevenchan.dto.response.BoardResponse;
import com.example.sixsevenchan.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public List<BoardResponse> findAll(){
        return boardService.findAll();
    }

    @GetMapping("/{id}")
    public BoardResponse findById(@PathVariable Long id){
        return boardService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardResponse save(@Valid @RequestBody BoardCreateRequest request){
        return boardService.save(request);
    }

}

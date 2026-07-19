package com.example.sixsevenchan.service;

import com.example.sixsevenchan.dto.request.BoardCreateRequest;
import com.example.sixsevenchan.dto.response.BoardResponse;
import com.example.sixsevenchan.entity.Board;
import com.example.sixsevenchan.exception.ResourceAlreadyExistsException;
import com.example.sixsevenchan.exception.ResourceNotFoundException;
import com.example.sixsevenchan.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponse save(BoardCreateRequest request){
        if (boardRepository.existsByPrefix(request.getPrefix())){
            throw new ResourceAlreadyExistsException("Доска с префиксом /"+ request.getPrefix() +" уже существует");
        }

        Board board = new Board();
        board.setPrefix(request.getPrefix());
        board.setName(request.getName());

        Board savedBoard = boardRepository.save(board);

        return new BoardResponse(
                savedBoard.getId(),
                savedBoard.getPrefix(),
                savedBoard.getName());
    }

    @Transactional(readOnly = true)
    public BoardResponse findById(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Борды не существует"));

        return new BoardResponse(
                board.getId(),
                board.getPrefix(),
                board.getName());

    }

    @Transactional(readOnly = true)
    public List<BoardResponse> findAll(){
        List<Board> boards = boardRepository.findAll();

        return boards.stream().map(board -> new BoardResponse(
                board.getId(),
                board.getPrefix(),
                board.getName())).toList();

    }

}

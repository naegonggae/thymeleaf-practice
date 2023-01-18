package com.team2.thymeleaf.controller;

import com.team2.thymeleaf.model.Board;
import com.team2.thymeleaf.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardRepository boardRepository;

    /** 포스트 등록 **/
    @PostMapping
    public Board add(@RequestBody Board board) {
        return boardRepository.save(board);
    }

    /** 포스트 조회 **/
    @GetMapping
    public List<Board> showAll(@RequestParam(required = false, defaultValue = "") String title,
                               @RequestParam(required = false, defaultValue = "") String content) {
        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
            return boardRepository.findAll();
        } else {
            return boardRepository.findByTitleOrContent(title, content);
            //http://localhost:8080/api/board?title=333&content=%EC%95%84%EC%98%A4
            //[{"id":1,"title":"레알하기싫어","content":"아오"},{"id":3,"title":"333","content":"333"}]
        }
    }

    /** 포스트 1개 조회 **/
    @GetMapping("/{id}")
    public Board showOne(@PathVariable Long id) {

        return boardRepository.findById(id).orElseThrow(()->new RuntimeException());
    }

    /** 포스트 수정 **/
    @PutMapping("/{id}")
    public Board modify(@RequestBody Board newBoard, @PathVariable Long id) {
        return boardRepository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return boardRepository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return boardRepository.save(newBoard);
                });
    }

    /** 포스트 삭제 **/
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        boardRepository.deleteById(id);
    }
}

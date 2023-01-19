package com.team2.thymeleaf.controller;
import com.team2.thymeleaf.validator.BoardValidator;
import jakarta.validation.Valid;

import com.team2.thymeleaf.model.Board;
import com.team2.thymeleaf.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable) {
        // http://localhost:8080/board/list?page=0
        // http://localhost:8080/board/list?page=0&size=1
        Page<Board> boards = boardRepository.findAll(pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        //boards.getTotalElements();
        model.addAttribute("boards", boards);
        return "/board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if (id == null) {
            model.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException());
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String submit(@Valid Board board, BindingResult bindingResult) {
        boardValidator.validate(board, bindingResult); // 내용검증
        if (bindingResult.hasErrors()) { // NotNull, Size 검증
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }
}

package com.team2.thymeleaf.controller;
import jakarta.validation.Valid;

import com.team2.thymeleaf.model.Board;
import com.team2.thymeleaf.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boards = boardRepository.findAll();
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
        if (bindingResult.hasErrors()) { // NotNull, Size 검증
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }
}

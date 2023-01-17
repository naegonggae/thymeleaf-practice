package com.team2.thymeleaf.controller;

import com.team2.thymeleaf.model.Board;
import com.team2.thymeleaf.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @GetMapping("/list")
    public String list() {

        return "/board/list";
    }
}

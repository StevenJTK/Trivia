package com.stevenjtk.trivia.controller;

import com.stevenjtk.trivia.dto.TriviaResponse;
import com.stevenjtk.trivia.service.TriviaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TriviaController {
    TriviaService triviaService;

    @GetMapping("/trivia")
    public TriviaResponse getTrivia() {
        return triviaService.getQuestions();
    }
}

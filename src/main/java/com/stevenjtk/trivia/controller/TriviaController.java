package com.stevenjtk.trivia.controller;

import com.stevenjtk.trivia.dto.TriviaQuestion;
import com.stevenjtk.trivia.service.TriviaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TriviaController {

    @Autowired
    TriviaService triviaService;

    /**
     * Mapping for fetching Trivia by calling the service class that has been
     * wired to fetch the questions.
     */

/*    @GetMapping("/questions")
    public List<TriviaQuestion> getTrivia() {
        return triviaService.getQuestions(userId); */
    }


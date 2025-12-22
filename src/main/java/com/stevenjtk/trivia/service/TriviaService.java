package com.stevenjtk.trivia.service;

import com.stevenjtk.trivia.dto.TriviaQuestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
public class TriviaService {
    private final RestTemplate restTemplate;
    private static final String API_URL = "https://the-trivia-api.com/v2/questions"; // Refactor and move it to dedicated package

   @Autowired
    public TriviaService(RestTemplate restTemplate) {
       this.restTemplate = restTemplate;
    }

    public List<TriviaQuestion> getQuestions(long userId) {
        TriviaQuestion[] questions = restTemplate.getForObject(API_URL, TriviaQuestion[].class);
        if (questions != null) {
            for (TriviaQuestion q : questions) {
                System.out.println("Question: " + q.getQuestion().getText());
                System.out.println("Options: " + shuffleAnswers(q));
                System.out.println("End");
            }
        } else {
            System.out.println("Questions could not be fetched from this API");
        }
        return questions != null ? Arrays.asList(questions) : List.of();
    }

    public List<String> shuffleAnswers(TriviaQuestion question) {
       List<String> answers = new ArrayList<>();

       answers.add(question.getCorrectAnswer());
       if(question.getIncorrectAnswers() != null) {
           answers.addAll(question.getIncorrectAnswers());
       }

       Collections.shuffle(answers);

        return answers;
    }
}

package com.stevenjtk.trivia.service;

import com.stevenjtk.trivia.dto.TriviaQuestion;
import com.stevenjtk.trivia.dto.TriviaResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class TriviaService {
    private final RestTemplate restTemplate;
    private static final String API_URL = "https://the-trivia-api.com/v2/questions";

    public TriviaService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    public List<TriviaQuestion> getQuestions() {
        TriviaQuestion[] questions = restTemplate.getForObject(API_URL, TriviaQuestion[].class);
        if (questions != null) {
            for (TriviaQuestion q : questions) {

                System.out.println("Question: " + q.getQuestion());
                System.out.println("Correct Answer: " + q.getCorrectAnswer());
                System.out.println("Incorrect Answers: " + q.getIncorrectAnswers());
                System.out.println("End");
            }
        } else {
            System.out.println("Questions could not be fetched from this API");
        }
        return questions != null ? Arrays.asList(questions) : List.of();
    }
}

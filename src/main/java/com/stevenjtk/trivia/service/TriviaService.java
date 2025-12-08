package com.stevenjtk.trivia.service;

import com.stevenjtk.trivia.dto.TriviaResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TriviaService {
    private final RestTemplate restTemplate;
    private static final String API_URL = "https://the-trivia-api.com/v2/questions";

    public TriviaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TriviaResponse getQuestions() {
        return restTemplate.getForObject(API_URL, TriviaResponse.class);
    }
}

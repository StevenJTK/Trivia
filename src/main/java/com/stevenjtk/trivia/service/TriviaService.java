package com.stevenjtk.trivia.service;

import com.stevenjtk.trivia.dto.TriviaQuestion;

import com.stevenjtk.trivia.dto.TriviaStart;
import com.stevenjtk.trivia.jda.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class TriviaService {

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://the-trivia-api.com/v2/questions"; // Refactor and move it to dedicated package
    private final Map<Long, GameState> activeGames = new ConcurrentHashMap<>();
    private final Map<Long, TriviaQuestion> activeTrivia = new ConcurrentHashMap<Long, TriviaQuestion>();

    @Autowired
    public TriviaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    TriviaStart triviaStart;

    public TriviaStart startGame(long userId, long channelId) {
        if (activeGames.containsKey(userId)) {
                return TriviaStart.error("No hijacking");
            }
            List<TriviaQuestion> questions = getQuestions(userId);
            if (questions.isEmpty()) {
                return TriviaStart.error("Question was not found");
            }

            Map<TriviaQuestion, List<String>> optionsMap = new HashMap<>();
            for (TriviaQuestion q : questions) {
                optionsMap.put(q, shuffleAnswers(q));
            }

            GameState newState = new GameState(userId, questions);
            activeGames.put(userId, newState);

            return TriviaStart.success(questions, optionsMap);
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

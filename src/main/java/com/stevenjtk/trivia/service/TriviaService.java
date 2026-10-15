package com.stevenjtk.trivia.service;

import com.stevenjtk.trivia.dto.TriviaQuestion;

import com.stevenjtk.trivia.dto.TriviaStart;
import com.stevenjtk.trivia.jda.GameState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TriviaService {

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://the-trivia-api.com/v2/questions"; // Refactor and move it to dedicated package
    private final Map<Long, GameState> activeGames = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(TriviaService.class);

    @Autowired
    public TriviaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean hasActiveGame(long userId) {
        return activeGames.containsKey(userId);
    }

    public TriviaStart startGame(long userId, long channelId) {
        if (activeGames.containsKey(userId)) {
            logger.info("User {} tried to answer a question while already having an active game session", userId);
            return TriviaStart.error("You already have a game session. Type answer if you want to give up");
        }

        List<TriviaQuestion> questions = getQuestions();
        logger.info("Fetched questions for user {}", userId);

        if (questions.isEmpty()) {
            return TriviaStart.error("Question was not found");
        }

        Map<TriviaQuestion, List<String>> optionsMap = new HashMap<>();

        for (TriviaQuestion q : questions) {
            logger.info("Fetched and randomized question output");
            optionsMap.put(q, shuffleAnswers(q, userId));

        }

        GameState newState = new GameState(userId, questions);
        activeGames.put(userId, newState);

        logger.info("New gamestate created for user {}", userId);
        return TriviaStart.success(questions, optionsMap);
    }

    public List<TriviaQuestion> getQuestions() {
        TriviaQuestion[] questions = restTemplate.getForObject(API_URL, TriviaQuestion[].class);
        logger.info("Fetching questions from API");
        return questions != null ? Arrays.asList(questions) : List.of();
    }

    public List<String> shuffleAnswers(TriviaQuestion question, long userId) {
        List<String> answers = new ArrayList<>();

        answers.add(question.getCorrectAnswer());
        if (question.getIncorrectAnswers() != null) {
            answers.addAll(question.getIncorrectAnswers());
        }
        logger.debug("Question '{}' shuffled for user {}", question.getQuestion().getText(), userId);
        Collections.shuffle(answers);
        return answers;
    }

    public void endGame(long userId) {
        logger.info("User {} has ended the game", userId);
        activeGames.remove(userId);
    }
}

package com.stevenjtk.trivia.jda;

import com.stevenjtk.trivia.dto.TriviaQuestion;

import java.util.List;


public class GameState {

    private final long playerId;
    private final List<TriviaQuestion> questions;
    private int currentIndex = 0;
    private int score = 0;

    public GameState(long playerId, List<TriviaQuestion> questions) {
        this.playerId = playerId;
        this.questions = questions;
    }

    public TriviaQuestion getQuestions() {
        return questions.get(currentIndex);
    }

    public boolean answer(String answer) {
        TriviaQuestion q = getQuestions();
        boolean correct = q.getCorrectAnswer().equals(answer);

        if(correct) score++;
        currentIndex++;
        return correct;
    }

    public boolean isFinished() {
        return currentIndex >= questions.size();
    }

    public int getScore() {
        return this.score;
    }

    public long getPlayerId() {
        return this.playerId;
    }
}

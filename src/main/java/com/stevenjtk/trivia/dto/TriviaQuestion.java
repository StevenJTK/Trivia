package com.stevenjtk.trivia.dto;

import com.stevenjtk.trivia.enums.Category;
import com.stevenjtk.trivia.enums.Difficulty;

import java.util.List;

/**
 * Behaves as the Question Model that the API has requested
 * Link: https://the-trivia-api.com/docs/v2/
 */

public class TriviaQuestion {
    private Category category;
    private String id;
    private List<String> tags;
    private Difficulty difficulty;
    private boolean isNiche;
    private Question question;
    private String correctAnswer;
    private List<String> incorrectAnswers;
    private String type;



    public Category getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public List<String> getTags() {
        return tags;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public boolean isNiche() {
        return isNiche;
    }

    public Question getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public String getType() {
        return type;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    public void setNiche(boolean isNiche) {
        this.isNiche = isNiche;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }
    public void setType(String type) {
        this.type = type;
    }
}



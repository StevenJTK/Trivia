package com.stevenjtk.trivia.dto;

import java.util.List;

public class TriviaQuestion {
    private String category;
    private String id;
    private List<String> tags;
    private String difficulty;
    private boolean isNiche = false;
    private TriviaResponse question;
    private String correctAnswer;
    private List<String> incorrectAnswers;
    private String type;


    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isNiche() {
        return isNiche;
    }

    public TriviaResponse getQuestion() {
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

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public void setNiche(boolean isNiche) {
        this.isNiche = isNiche;
    }
    public void setQuestion(TriviaResponse question) {
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



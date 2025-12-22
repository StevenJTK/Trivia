package com.stevenjtk.trivia.dto;

import java.util.List;
import java.util.Map;

public class TriviaStart {

    private final boolean success;
    private final String error;
    private final List<TriviaQuestion> questions;
    private final Map<TriviaQuestion, List<String>> options;


    private TriviaStart(boolean success, String error, List<TriviaQuestion> questions, Map<TriviaQuestion, List<String>> options) {
        this.success = success;
        this.error = error;
        this.questions = questions;
        this.options = options;
    }


    public static TriviaStart error(String message) {
        return new TriviaStart(false, message, List.of(), Map.of());
    }


    public static TriviaStart success(TriviaQuestion question, List<String> options) {
        return new TriviaStart(true,
                null,
                List.of(question),
                Map.of(question, options));
    }


    public static TriviaStart success(List<TriviaQuestion> questions,
                                      Map<TriviaQuestion, List<String>> optionsMap) {
        return new TriviaStart(true, null, questions, optionsMap);
    }


    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public List<TriviaQuestion> getQuestions() {
        return questions;
    }

    public Map<TriviaQuestion, List<String>> getOptions() {
        return options;
    }
}

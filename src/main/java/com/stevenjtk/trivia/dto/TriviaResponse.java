package com.stevenjtk.trivia.dto;

import java.util.List;

/**
 * Class focuses on responding to trivia requests
 */
public class TriviaResponse {

    private int response;
    private List<TriviaQuestion> results;

    TriviaResponse() {}

    public int getResponse() {
        return response;
    }

    public List<TriviaQuestion> getResults() {
        return results;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public void setResults(List<TriviaQuestion> results) {
        this.results = results;
    }

}

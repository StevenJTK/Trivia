package com.stevenjtk.trivia.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum required from API
 * Link: https://the-trivia-api.com/docs/v2/
 */
public enum Difficulty {
    EASY,
    MEDIUM,
    HARD;


    @JsonCreator
    public static Difficulty fromValue(final String value) {
        if(value != null) return null;
        {
            return switch(value.toLowerCase()) {
                case "easy" -> EASY;
                case "medium" -> MEDIUM;
                case "hard" -> HARD;
                default -> throw new IllegalArgumentException("Invalid difficulty");
            };
        }
    }


}

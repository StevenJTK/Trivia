package com.stevenjtk.trivia.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Required Enum from the API
 * Link: https://the-trivia-api.com/docs/v2/
 */
public enum Category {
    MUSIC,
    SPORT_AND_LEISURE,
    FILM_AND_TV,
    ARTS_AND_LITERATURE,
    HISTORY,
    SOCIETY_AND_CULTURE,
    SCIENCE,
    GEOGRAPHY,
    FOOD_AND_DRINK,
    GENERAL_KNOWLEDGE;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Category fromValue(String value) {
        if (value == null) return null;
        {
            return switch (value.toLowerCase()) {
                case "music" -> MUSIC;
                case "sport_and_leisure" -> SPORT_AND_LEISURE;
                case "film_and_tv" -> FILM_AND_TV;
                case "arts_and_literature" -> ARTS_AND_LITERATURE;
                case "history" -> HISTORY;
                case "society_and_culture" -> SOCIETY_AND_CULTURE;
                case "science" -> SCIENCE;
                case "geography" -> GEOGRAPHY;
                case "food_and_drink" -> FOOD_AND_DRINK;
                case "general_knowledge" -> GENERAL_KNOWLEDGE;
                default -> throw new IllegalArgumentException("Unknown category: " + value);
            };
        }
    }
}

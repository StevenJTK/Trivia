package com.stevenjtk.trivia.api;

import com.stevenjtk.trivia.commands.TriviaResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TriviaFetcher {

    /**
     * Method for fetching trivia question via API
     * We try an HTTPRequest at the given API (no key required), which we then get and finally build.
     * @return We return the data with a response body and status code.
     * Lastly we have a catch Exception in case the data is not retrieved, with a 500 server error.
     */

    public static TriviaResponse getTrivia() {

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://the-trivia-api.com/v2/questions"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return new TriviaResponse(response.statusCode(), response.body());

        } catch (Exception e) {
            e.printStackTrace();
            return new TriviaResponse(500, e.getMessage());

        }
    }
}

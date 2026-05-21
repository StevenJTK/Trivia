package com.stevenjtk.trivia.service;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LimitService {

    private final TriviaService triviaService;
    private final Map<Long, Integer> dailyGameCount = new ConcurrentHashMap();

    public LimitService(TriviaService triviaService) {
        this.triviaService = triviaService;
    }

   public boolean limitReached(long userId) {
        if (triviaService.hasActiveGame(userId)) return true;

        int gamesPlayed = dailyGameCount.getOrDefault(userId, 0);
        return gamesPlayed >= 100;
   }

   public void recordGame(long userId) {
        dailyGameCount.merge(userId, 1, Integer::sum);
   }

   public void resetGameCounts() {
        dailyGameCount.clear();
   }
}

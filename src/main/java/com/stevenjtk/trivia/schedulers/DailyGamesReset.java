package com.stevenjtk.trivia.schedulers;

import com.stevenjtk.trivia.service.LimitService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyGamesReset {

    private final LimitService limitService;

    public DailyGamesReset(LimitService limitService) {
        this.limitService = limitService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetDailyCounts() {
        limitService.resetGameCounts();
    }
}

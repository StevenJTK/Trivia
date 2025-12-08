package com.stevenjtk.trivia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Comment from post coding interview during Enterprise program
// What is a configuration? It is a class that contains bean definitions -
// Spring automatically loads all the beans into the IoC container.
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

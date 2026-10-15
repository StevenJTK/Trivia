package com.stevenjtk.trivia.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Comment from post coding interview during Enterprise program
// What is a configuration? It is a class that contains bean definitions -
// Spring automatically loads all the beans into the IoC container.
@Configuration
public class AppConfig {

    /**
     * / RestTemplate is a provided class from the Spring Framework that simplifies HTTP requests and handling responses.
     * / It makes it easier to interact with RESTful web services as it abstracts away the boilerplate code.
     */

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }
}

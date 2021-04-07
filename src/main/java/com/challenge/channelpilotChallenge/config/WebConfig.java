package com.challenge.channelpilotChallenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class WebConfig {

    @Value("${note.consume.count_of_workers}")
    private int countOfWorkers;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(countOfWorkers);
    }
}

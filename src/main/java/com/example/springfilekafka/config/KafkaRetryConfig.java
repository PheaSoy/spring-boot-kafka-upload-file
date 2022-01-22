package com.example.springfilekafka.config;

import com.example.springfilekafka.CustomRetryTopicNamesProviderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.retrytopic.RetryTopicNamesProviderFactory;

@Configuration
public class KafkaRetryConfig {
    @Bean
    public RetryTopicNamesProviderFactory myRetryNamingProviderFactory() {
        return new CustomRetryTopicNamesProviderFactory();
    }
}

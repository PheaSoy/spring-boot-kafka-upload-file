package com.example.springfilekafka.config;

import org.springframework.kafka.retrytopic.DestinationTopic;
import org.springframework.kafka.retrytopic.RetryTopicNamesProviderFactory;
import org.springframework.kafka.retrytopic.SuffixingRetryTopicNamesProviderFactory;

public class CustomRetryTopicNamesProviderFactory implements RetryTopicNamesProviderFactory {

	@Override
    public RetryTopicNamesProvider createRetryTopicNamesProvider(
                DestinationTopic.Properties properties) {

        if(properties.isMainEndpoint()) {
            return new SuffixingRetryTopicNamesProviderFactory.SuffixingRetryTopicNamesProvider(properties);
        }
        else {
            return new SuffixingRetryTopicNamesProviderFactory.SuffixingRetryTopicNamesProvider(properties) {

                @Override
                public String getTopicName(String topic) {
                    return "retry-bulk-" + super.getTopicName(topic);
                }

            };
        }
    }

}
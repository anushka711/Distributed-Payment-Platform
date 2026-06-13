package com.anushka.transactionprocessor.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic paymentProcessedTopic() {

        return TopicBuilder
                .name("payment-processed")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
public NewTopic paymentRetryTopic() {

    return TopicBuilder
            .name("payment-retry")
            .partitions(3)
            .replicas(1)
            .build();
}

@Bean
public NewTopic paymentDlqTopic() {

    return TopicBuilder
            .name("payment-dlq")
            .partitions(3)
            .replicas(1)
            .build();
}
}
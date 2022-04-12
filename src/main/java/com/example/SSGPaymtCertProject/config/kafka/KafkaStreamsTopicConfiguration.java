package com.example.SSGPaymtCertProject.config.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.apache.kafka.clients.admin.NewTopic;
@Configuration
public class KafkaStreamsTopicConfiguration {

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("streamsTopic").build();
    }
}

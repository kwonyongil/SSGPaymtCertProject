package com.example.SSGPaymtCertProject.config.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

/**
 * Consumer 가 특정 데이터를 전달 받았는지 여부를 확인 할 수 있음
 */
@Configuration
public class ReplyingKafkaTemplateConfiguration {

    private final ConcurrentKafkaListenerContainerFactory<String, String> containerFactory;

    public ReplyingKafkaTemplateConfiguration(ConcurrentKafkaListenerContainerFactory<String, String> containerFactory) {
        this.containerFactory = containerFactory;
    }

    // key, value, 응답해주는 객체 타입까지 지정해준다.
    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(ProducerFactory<String, String> producerFactory,
                                                                               ConcurrentMessageListenerContainer<String, String> relpiesContainer) {
        // 프로듀서 팩토리, 메시지 리스너 컨테이너가 필요하다. (다시 발행해야 하므로)
        return new ReplyingKafkaTemplate<>(producerFactory, relpiesContainer);
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, String> relpiesContainer() {
        // reply 토픽에 대한 헤더정보가 replyTestTopic 가 된다.
        ConcurrentMessageListenerContainer<String, String> container =
                containerFactory.createContainer("replyTestTopic");
        container.getContainerProperties().setGroupId("ssg-pg-reply-container-id");
        return container;
    }
}

package com.example.SSGPaymtCertProject.config.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * KafkaTemplate 은 스프링부트에서 AutoConfigure 된다.
 * 해당 클래스는 디폴트 KafkaTemplate 처럼 KafkaTemplate 을 생성해보는 학습용 클래스다.
 * @ConditionalOnMissingBean 어노테이션에 의해 KafkaTemplate 빈이 정의되어 있지 않을 때만 기본 빈으로 동작한다.
 */
@Configuration
public class KafkaTemplateConfiguration {

    @Bean
    KafkaTemplate kafkaTemplate() {
        return new KafkaTemplate(producerFactory());
    }

    private ProducerFactory<String, String> producerFactory() {
        return  new DefaultKafkaProducerFactory<>(producerProps());
    }

    private Map<String, Object> producerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return  props;
    }
}

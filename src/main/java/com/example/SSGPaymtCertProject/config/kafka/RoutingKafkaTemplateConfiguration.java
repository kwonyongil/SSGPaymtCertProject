package com.example.SSGPaymtCertProject.config.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.RoutingKafkaTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 토픽명은 Regular Expression (정규식) 으로 표현 가능
 * 정규식으로 구분된 토픽은 각각 다른 ProducerFactory 를 이용할 수 있다.
 * transactions, execute, flush, metric 커맨드를 지원하지 않음
 */
@Configuration
public class RoutingKafkaTemplateConfiguration {

    @Bean
    RoutingKafkaTemplate routingKafkaTemplate() {
        return new RoutingKafkaTemplate(factories());
    }

    private Map<Pattern, ProducerFactory<Object, Object>> factories() {
        Map<Pattern, ProducerFactory<Object, Object>> factories = new LinkedHashMap<>();
        // 패턴에 정규 표현식은 따로 학습
        factories.put(Pattern.compile("certTopic"), byteProducerFactory());
        factories.put(Pattern.compile(".*"), defaultKafkaProducerFactory());
        return factories;
    }

    private ProducerFactory<Object, Object> byteProducerFactory() {
        Map<String, Object> props = producerProps();
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    // 어떤 타입으로 직렬화가 될 지 모르기때문에 <Object, Object> 타입이다.
    private ProducerFactory<Object, Object> defaultKafkaProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerProps());
    }

    private Map<String, Object> producerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return  props;
    }
}

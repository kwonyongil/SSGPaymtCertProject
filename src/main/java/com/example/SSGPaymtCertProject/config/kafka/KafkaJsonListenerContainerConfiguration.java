package com.example.SSGPaymtCertProject.config.kafka;

import com.example.SSGPaymtCertProject.domain.dto.OrdDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Json 객체와 Validation 체크를 하기위한 리스너 설정 클래스
 */
@Configuration
public class KafkaJsonListenerContainerConfiguration implements KafkaListenerConfigurer {

    // Validator 는 만들 수 있지만 우선 만들어져있는 Validator 로 테스트
    private final LocalValidatorFactoryBean validator;

    public KafkaJsonListenerContainerConfiguration(LocalValidatorFactoryBean validator) {
        this.validator = validator;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrdDto>> kafkaJsonContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrdDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ordDtoConsumerFactory());
        return factory;
    }

    private ConsumerFactory<String, OrdDto> ordDtoConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                props(),
                new StringDeserializer(),
                new JsonDeserializer<>(OrdDto.class));
    }

    private Map<String, Object> props() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }


    @Override
    public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
        registrar.setValidator(validator);
    }
}

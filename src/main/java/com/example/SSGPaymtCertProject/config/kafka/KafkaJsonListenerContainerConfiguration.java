package com.example.SSGPaymtCertProject.config.kafka;

import com.example.SSGPaymtCertProject.domain.dto.OrdDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrdDto>> kafkaJsonContainerFactory(
            KafkaTemplate<String, OrdDto> kafkaJsonTemplate
    ) {
        ConcurrentKafkaListenerContainerFactory<String, OrdDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ordDtoConsumerFactory());
        // 예외 발생시 재시도 설정
        /*
        factory.setRetryTemplate(customizedRetryTemplate());
        factory.setRecoveryCallback(context -> {
            ConsumerRecord record = (ConsumerRecord) context.getAttribute("record");
            System.out.println("Recovery callback. message=" + record.value());
            // ErrorHandler 테스트를 위해 강제 예외 발생
            throw new RuntimeException("RuntimeException!");
            //return Optional.empty();
        });
        // 컨테이너 에러가 발생해야지만 동작하는데 RecoveryCallback 에서 정상적인 처리가 이루어지면 동작하지 못한다.
        // RecoveryCallback 안에서 에러가 발생시켜
        factory.setErrorHandler((thrownException, data) -> System.out.println("Error Handler. exception=" + thrownException.getMessage()));
        */

        // DeadLetterPublishingRecoverer : 시도했던 토픽명 + .DLT , partition 을 실패했던 메시지 그대로 돌려준다.
        factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(kafkaJsonTemplate)));

        return factory;
    }

    // fixedBackoff : interval 1초
    // customPolicy : 몇번 반복할지, 어떤 예외에 대해 처리할지 정함
    private RetryTemplate customizedRetryTemplate() {
        return new RetryTemplateBuilder()
                .fixedBackoff(1_000)
                .customPolicy(retryPolicy())
                .build();
    }

    private RetryPolicy retryPolicy() {
        Map<Class<? extends Throwable>, Boolean> exceptions = new HashMap<>();
        // true : 에러가 발생되었을 때 재시도, false : 재시도 x
        exceptions.put(ListenerExecutionFailedException.class, true);
        return new SimpleRetryPolicy(3, exceptions);
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

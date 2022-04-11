package com.example.SSGPaymtCertProject.config.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * KafkaAdmin 은 springboot 에서 AutoConfigure 되어 자동으로 만들어진다.
 * 컨텍스트가 초기화가 될때 initialize 메서드가 실행된다.
 * autoCreate 를 false 로 하면 토픽이 생성되지 않고
 * 디폴트인 true로 두면 토픽이 생성 로직이 실행된다.
 * 두가지 빈타입 NewTopic 과 NewTopic 를 이용해 컬렉션을 만든다.
 * addOrModifyTopicsIfNeeded 메서드를 통해 토픽을 구분한다.
 * 먼저 토픽의 정보를 조회한 뒤 checkPartitions 메서드가 실행된다.
 * 토픽의 정보를 가지고와서 이미 존재하지 않는 객체(토픽)면 ExcutionException 발생한다.
 * 예외가 발생한뒤 존재하지 않는 그 토픽을 추가하기 위한 컬렉션에 넣는다.
 * 체크한 후 topicsToAdd 추가해야하는 토픽과 topicsModify 수정해야하는 토픽을 작업한다.
 * 토픽의 파티션의 개수가 이미 존재하는 토픽의 파티션의 개수보다 적으면 동작하지 않고,
 * 존재하는 것보다 크면 modify 하게된다.
 */
@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public AdminClient adminClient(KafkaAdmin kafkaAdmin) {
        return AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    @Bean
    public NewTopic ordTopic() {
        return TopicBuilder.name("ordTopic").build();
    }

    @Bean
    public KafkaAdmin.NewTopics ssgPaymtCertTopic() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("userTopic").build(),
                TopicBuilder.name("itemTopic").build(),
                TopicBuilder.name("certTopic").build(),
                TopicBuilder.name("replyTestReqTopic").build(),
                TopicBuilder.name("replyTestResTopic").build(),
                TopicBuilder.name("testListenerTopic").build(),
                TopicBuilder.name("paymtTopic").partitions(3)
                        .replicas(1)
                        .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(1000 * 60 * 60))
                        .build()
        );
    }
}

package com.example.SSGPaymtCertProject.service.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@SpringBootTest
public class kafkaTopicTest {

    @Autowired
    AdminClient adminClient;
    /**
     * 단위테스트 시작 전 공통적으로 초기화되는 코드
     */
    @BeforeEach
    public void setUp() {

    }

    @Test
    public void 카프카_토픽_테스트() throws ExecutionException, InterruptedException {
        Map<String, TopicListing> topics = adminClient.listTopics().namesToListings().get();
        for (String topicName : topics.keySet()) {
            TopicListing topicListing = topics.get(topicName);
            System.out.println(topicListing);
            // 토픽 정보를 조금 더 자세히 보기위해서는 describeTopics 를 사용한다.
            Map<String, TopicDescription> descriptionMap = adminClient.describeTopics(Collections.singleton(topicName)).all().get();
            System.out.println(descriptionMap);
            // 토픽을 제거하기전 인터널 토픽은 중요하므로 인터널 토픽 체크를 안전하게 반드시 하도록하자
            if (!topicListing.isInternal()) {
                adminClient.deleteTopics(Collections.singleton(topicName));
            }
        }
    }

    @AfterEach
    public void tearDown() {
    }
}

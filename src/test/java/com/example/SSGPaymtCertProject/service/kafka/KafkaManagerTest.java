package com.example.SSGPaymtCertProject.service.kafka;

import com.example.SSGPaymtCertProject.service.kafka.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;


@SpringBootTest
public class KafkaManagerTest {

    @Autowired
    KafkaManager kafkaManager;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    KafkaConsumer kafkaConsumer;

    /**
     * 단위테스트 시작 전 공통적으로 초기화되는 코드
     */
    @BeforeEach
    public void setUp() {

    }

    @Test
    public void 카프카매니저_토픽_테스트1() throws ExecutionException, InterruptedException {
        kafkaManager.describeTopicConfigs();
        kafkaManager.changeConfig();
//        kafkaManager.deleteRecords();
        kafkaManager.findAllConsumerGroups();
        kafkaManager.findAllOffsets();
//        try {
//            kafkaManager.deleteConsumerGroup();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Thread.sleep(2_000L);
//        System.out.println("---after delete consumer ---");
//        kafkaManager.findAllConsumerGroups();

        kafkaTemplate.send("offsetTopic-listener", "hello offsetTopic-listener");
        kafkaConsumer.seek();
    }
    

    @AfterEach
    public void tearDown() {
    }
}

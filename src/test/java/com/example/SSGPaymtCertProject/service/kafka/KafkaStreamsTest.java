package com.example.SSGPaymtCertProject.service.kafka;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Date;

@SpringBootTest
public class KafkaStreamsTest {

    @Autowired
    KafkaTemplate kafkaTemplate;
    /**
     * 단위테스트 시작 전 공통적으로 초기화되는 코드
     */
    @BeforeEach
    public void setUp() {

    }

    @Test
    public void 카프카스트림즈_테스트() throws InterruptedException {
        while (true) {
            kafkaTemplate.send("streamsTopic", "Hello, Streams!");
            Thread.sleep(1_000);
        }
    }

    @Test
    public void 카프카스트림즈_테스트2() throws InterruptedException {
        while (true) {
            kafkaTemplate.send("streamsTopic", String.valueOf(new Date().getTime()));
            Thread.sleep(1_000);
        }
    }

    @AfterEach
    public void tearDown() {
    }
}

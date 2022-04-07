package com.example.SSGPaymtCertProject.service.kafka;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@SpringBootTest
public class kafkaProducerTest {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    KafkaProducer kafkaProducer;

    /**
     * 단위테스트 시작 전 공통적으로 초기화되는 코드
     */
    @BeforeEach
    public void setUp() {

    }

    @Test
    public void 카프카프로듀스_메시지_테스트1() {
        kafkaTemplate.send("userTopic", "hello-userTopic");
    }

    @Test
    public void 카프카프로듀스_메시지_테스트2() {
        kafkaTemplate.send("quickstart-events", "hello-wrold");
    }

    @Test
    public void 카프카프로듀스_메시지_테스트3() {
        kafkaProducer.async("paymtTopic", "hello-asyncTopic");
        kafkaProducer.sync("paymtTopic", "hello-syncTopic");
    }

    @Test
    public void 카프카프로듀스_메시지_테스트4() {
        kafkaProducer.routingSend("itemTopic", "hello-itemTopic");
        kafkaProducer.routingSendBytes("certTopic", "hello-bytesCertTopic".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void 카프카프로듀스_메시지_테스트5() throws ExecutionException, InterruptedException, TimeoutException {
        kafkaProducer.replyingSend("replyTestReqTopic", "ping ReplyTopic!");
    }

    @AfterEach
    public void tearDown() {
    }
}

package com.example.SSGPaymtCertProject.service.kafka;

import com.example.SSGPaymtCertProject.domain.dto.OrdDto;
import com.example.SSGPaymtCertProject.service.kafka.producer.KafkaProducer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

import java.util.Date;

@SpringBootTest
public class KafkaConsumerTest {

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    KafkaMessageListenerContainer kafkaMessageListenerContainer;

    /**
     * 픽스처
     */
    OrdDto ordDto1;

    OrdDto ordDto2;

    /**
     * 단위테스트 시작 전 공통적으로 초기화되는 코드
     */
    @BeforeEach
    public void setUp() {
        this.ordDto1 = OrdDto.builder()
                .ordNo("test1")
                .orordNo("test2")
                .ordRcpDts(new Date())
                .build();

        this.ordDto2 = OrdDto.builder()
                .orordNo("test2")
                .ordRcpDts(new Date())
                .build();
    }

    @Test
    public void 카프카리스너_제어_테스트1() {
        kafkaMessageListenerContainer.start();
    }

    @Test
    public void 카프카리스너_컨슈머_테스트1() throws InterruptedException {
        kafkaProducer.async("testListenerTopic", "Hello-listener-container test!");
        // container.setAutoStartup(true) 로 설정하면 start() 없이 컨텍스트가 올라올때 자동 실행된다.
        kafkaMessageListenerContainer.start();
        Thread.sleep(1_000L);
        System.out.println("---pause---");
        kafkaMessageListenerContainer.pause();
        Thread.sleep(5_000L);

        kafkaProducer.async("testListenerTopic", "Hello-Secondly-listener-container test!");
        // 다시 시작하고 싶을 때
        System.out.println("---resume---");
        kafkaMessageListenerContainer.resume();
        Thread.sleep(1_000L);

        // 해당 메서드들을 이용해 특정한 리스너 컨테이너만 중단시킬 수 있다.
        System.out.println("---stop---");
        kafkaMessageListenerContainer.stop();
    }

    @Test
    public void 카프카리스너_컨슈머_테스트2() {
        // KafkaConsumer.listenDefaultListener, Kaf aConsumer.listenMetaData 테스트
        kafkaProducer.async("testListenerTopic-listener", "Hello-testListenerTopic-container");
    }

    @Test
    public void 카프카리스너_컨슈머_테스트3() {
        // KafkaConsumer.listenOrdDto 테스트
        kafkaProducer.async("ordDtoTopic", ordDto1);
    }

    @Test
    public void 카프카리스너_컨슈머_테스트4() {
        // KafkaConsumer.listenOrdDto 테스트, 발리데이션 체크용 ordNo 빈값, 에러 발생!
        kafkaProducer.async("ordDtoTopic", ordDto2);
    }

    @AfterEach
    public void tearDown() {
    }
}

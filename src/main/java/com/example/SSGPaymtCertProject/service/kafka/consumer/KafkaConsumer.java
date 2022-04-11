package com.example.SSGPaymtCertProject.service.kafka.consumer;

import com.example.SSGPaymtCertProject.domain.dto.OrdDto;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;

/**
 * Kafka 컨슈머 실습
 * AbstractConsumerSeekAware : ConsumerSeekAware 기본 제공
 */
@Service
public class KafkaConsumer extends AbstractConsumerSeekAware {

    private final Counter counter;

    public KafkaConsumer(MeterRegistry meterRegistry) {
        this.counter = meterRegistry.counter("ssg-pg-id-counter","group_id", "ssg-pg-id");
    }

    @KafkaListener(id = "ssg-pg-id", topics = "quickstart-events")
    public void listen(String message) {
        System.out.println("=============");
        System.out.println(message);
        System.out.println("=============");
        counter.increment();
    }

    @KafkaListener(id = "ssg-pg-item-id", topics = "itemTopic")
    public void listenItem(String message) {
        System.out.println(message);
    }

    @KafkaListener(id = "ssg-pg-cert-bytes-id", topics = "certTopic")
    public void listenCertBytes(String message) {
        System.out.println(message);
    }

    // @SendTo 애노테이션은 리턴되는 타입을 그대로 다시 발송시켜줌
    @KafkaListener(id = "ssg-pg-reply-req-id", topics = "replyTestReqTopic")
    @SendTo
    public String listenReplyReq(String message) {
        System.out.println(message);
        return "Pong ReplyTopic!";
    }

    // concurrency : 쓰레드 수, 파티션이 많다면 쓰레드도 늘리면 좋다.
    // clientIdPrefix : group_id 를 기준으로 client_id 가 지정되는데 이를 내가 지정할 수 있다.
    @KafkaListener(id = "testListenerTopic-listener-id", topics = "testListenerTopic-listener",
        concurrency = "2", clientIdPrefix = "listener-id")
    public void listenDefaultListener(String message) {
        System.out.println("Listener message : " + message);
    }

    @KafkaListener(id = "testListenerTopic-meta-id", topics = "testListenerTopic-listener")
    public void listenMetaData(String message,
                               @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) long partition,
                               @Header(KafkaHeaders.OFFSET) long offset,
                               ConsumerRecordMetadata metadata) {
        System.out.println(
                "Listener offset : " + metadata.offset() +
                " timestamp = " + new Date(timestamp) +
                " partition = " + partition +
                " offset = " + offset
        );
    }

    @KafkaListener(id = "ordDtoTopic-listener", topics = "ordDtoTopic",
            containerFactory = "kafkaJsonContainerFactory")
    public void listenOrdDto(@Valid OrdDto ordDto) {
        System.out.println("Listener ordDto : " + ordDto);
    }

    @KafkaListener(id = "offsetTopic-listener-id", topics = "offsetTopic-listener")
    public void listenOffset(String message) {
        System.out.println("message = " + message);
    }

    public void seek() {
        // 1개의 특정 카프카의 리스너의 토픽과 파티션 탐색
        // getSeekCallbackFor()
        // 전체 카프카의 리스너 토픽과 파티션 탐색
        getSeekCallbacks().forEach(
                (tp, consumerSeekCallback) -> consumerSeekCallback.seek(tp.topic(), tp.partition(), 0)
        );
    }

}

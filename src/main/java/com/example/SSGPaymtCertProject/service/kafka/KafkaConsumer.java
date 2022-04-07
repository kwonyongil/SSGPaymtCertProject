package com.example.SSGPaymtCertProject.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * Kafka 컨슈머 실습
 */
@Component
public class KafkaConsumer {

    @KafkaListener(id = "ssg-pg-id", topics = "quickstart-events")
    public void listen(String message) {
        System.out.println("=============");
        System.out.println(message);
        System.out.println("=============");
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
}

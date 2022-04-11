package com.example.SSGPaymtCertProject.service.kafka.producer;

import com.example.SSGPaymtCertProject.domain.dto.OrdDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Kafka 프로듀서 실습
 */
@Component
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaTemplate<String, OrdDto> kafkaJsonTemplate;

    private final RoutingKafkaTemplate routingKafkaTemplate;

    private final ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, KafkaTemplate<String, OrdDto> kafkaJsonTemplate,
                         RoutingKafkaTemplate routingKafkaTemplate, ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaJsonTemplate = kafkaJsonTemplate;
        this.routingKafkaTemplate = routingKafkaTemplate;
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    /* 비동기 실행 */
    public void async(String topic, String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

        // ListenableFutureCallback -> 프로듀스 결과를 예외발생의 경우만 알 수 있다.
        //future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

        // KafkaSendCallback 는 실패한 메시지를 확인할 수 있다.
        future.addCallback(new KafkaSendCallback<String, String>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Success to Send Message. ");
            }

            @Override
            public void onFailure(KafkaProducerException ex) {
                ProducerRecord<Object, Object> record = ex.getFailedProducerRecord();
                System.out.println("Fail to Send Message. record=" + record);
            }

        });
    }

    /* 객체 비동기 실행 */
    public void async(String topic, OrdDto ordDto) {
        kafkaJsonTemplate.send(topic, ordDto);
    }

    /* 동기 실행 */
    public void sync(String topic, String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

        try {
            System.out.println("Success to Send Sync Message");
            future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void routingSend(String topic, String message) {
        routingKafkaTemplate.send(topic, message);
    }

    public void routingSendBytes(String topic, byte[] message) {
        routingKafkaTemplate.send(topic, message);
    }

    public void replyingSend(String topic, String message) throws ExecutionException, InterruptedException, TimeoutException {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        RequestReplyFuture<String, String, String> replyFuture = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, String> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        System.out.println(consumerRecord.value());
    }
}

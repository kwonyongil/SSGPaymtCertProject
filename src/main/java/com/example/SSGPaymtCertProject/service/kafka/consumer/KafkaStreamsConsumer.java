package com.example.SSGPaymtCertProject.service.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaStreamsConsumer {

    @KafkaListener(id = "streamsTopic-to-listener", topics = "streamsTopic-to")
    public void listen(String message) {
        System.out.println("Listener. message=" + message);
    }
}

package com.example.SSGPaymtCertProject.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class KafkaTestController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaTestController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/produce")
    public String produce() throws InterruptedException {
        while (true) {
            kafkaTemplate.send("quickstart-events", String.valueOf(new Date().getTime()));
            Thread.sleep(100L);
        }
    }
}

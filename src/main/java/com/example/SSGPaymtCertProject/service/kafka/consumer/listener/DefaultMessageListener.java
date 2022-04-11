package com.example.SSGPaymtCertProject.service.kafka.consumer.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

public class DefaultMessageListener implements MessageListener<String, String> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageListener.class);

    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        System.out.println("Default Message Listener, Message : " + data.value());
        logger.info("Default Message Listener, Message : {}", data.value());
    }
}

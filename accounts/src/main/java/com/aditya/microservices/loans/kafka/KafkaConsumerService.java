package com.aditya.microservices.loans.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "order-topic", groupId = "inventory-group")
    public void processOrderEvent(ConsumerRecord<String, String> record) {
        String orderId = record.value();
        System.out.println("Received order event: " + orderId);
    }
}

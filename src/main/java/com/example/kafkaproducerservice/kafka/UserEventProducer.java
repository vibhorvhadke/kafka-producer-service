package com.example.kafkaproducerservice.kafka;

import com.example.kafkaproducerservice.dto.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    private static final String TOPIC = "user-events";

    public void publishUserEvent(UserEvent event) {
        kafkaTemplate.send(TOPIC, event.getUserId(), event);
    }
}
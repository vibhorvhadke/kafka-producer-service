package com.example.kafkaproducerservice.controller;

import com.example.kafkaproducerservice.dto.UserEvent;
import com.example.kafkaproducerservice.kafka.UserEventProducer;
import com.example.kafkaproducerservice.model.User;
import com.example.kafkaproducerservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserEventProducer userEventProducer;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);

        UserEvent event = new UserEvent(
                "USER_CREATED",
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );

        userEventProducer.publishUserEvent(event);

        return ResponseEntity.ok(savedUser);
    }
}
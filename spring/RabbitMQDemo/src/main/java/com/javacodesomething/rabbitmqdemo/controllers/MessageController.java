package com.javacodesomething.rabbitmqdemo.controllers;

import com.javacodesomething.rabbitmqdemo.publishers.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MessageController {

    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public MessageController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }


    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(
            @RequestParam("message") String message
    ) {

        rabbitMQProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }
}

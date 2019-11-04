package com.banzaicloud.spotguide;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/kafka")
@ConditionalOnProperty(value = "spring.kafka.enabled", havingValue = "true")
public class KafkaController {

    static final String BOOT_TOPIC = "spring-boot";

    private final List<String> messages = new ArrayList<>();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping
    public void sendMessage(@RequestBody Map<String, String> body) {
        kafkaTemplate.send(BOOT_TOPIC, body.get("message"));
    }

    @GetMapping
    public List<String> listMessages() {
        return messages;
    }

    @KafkaListener(topics = KafkaController.BOOT_TOPIC)
    public void consume(String message) {
        messages.add(message);
    }
}

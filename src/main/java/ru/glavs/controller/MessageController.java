package ru.glavs.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Controller
@EnableWebSocketMessageBroker
@Slf4j
public class MessageController {

    private static final String TOPIC = "/topic/message";
    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @KafkaListener(id = "SpringConsumer", topicPartitions = {
            @TopicPartition(topic = "character-events", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))
    })
    public void listen(String message) {
        log.info("Got message from Kafka: {}", message);
        simpMessagingTemplate.convertAndSend(TOPIC, message);
    }
}

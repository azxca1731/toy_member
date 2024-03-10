package com.azxca1731.member.consumer;

import com.azxca1731.member.domain.pii.RetentionService;
import com.azxca1731.member.domain.sign.kafka.SignOutTopicMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignOutConsumer {
    private final static String TOPIC = "member.sign.out.1";
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final RetentionService retentionService;

    @KafkaListener(topics = TOPIC)
    public void listen(String jsonMessage) {
        try {
            SignOutTopicMessage message = OBJECT_MAPPER.readValue(jsonMessage, SignOutTopicMessage.class);
            retentionService.migrateUserInfo(message.getMemberId()+"");
        } catch (RuntimeException | JsonProcessingException e) {
            log.error("topic consumed error. topic: {}, message: {}\n{}", TOPIC, jsonMessage, e);
        }
    }
}
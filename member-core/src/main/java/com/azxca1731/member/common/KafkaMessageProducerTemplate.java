package com.azxca1731.member.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
public abstract class KafkaMessageProducerTemplate<T> {

    protected abstract String getTopic();

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Async("kafkaExecutor")
    public void send(T message) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(getTopic(), message);

        try {
            SendResult<String, Object> result = future.get();
            log.debug("kafka send well. topic: {}, offset: {}, partition: {}", getTopic(), result.getRecordMetadata().offset(), result.getProducerRecord().partition());
        } catch (RuntimeException | InterruptedException | ExecutionException e) {
            log.error("kafka doesn't produce. topic:{}. input: {}\n{}", getTopic(), message, e);
        }
    }
}

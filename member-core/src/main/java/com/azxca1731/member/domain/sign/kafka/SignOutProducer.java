package com.azxca1731.member.domain.sign.kafka;

import com.azxca1731.member.common.KafkaMessageProducerTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SignOutProducer extends KafkaMessageProducerTemplate<SignOutTopicMessage> {
    private final static String TOPIC = "member.sign.out.1";

    public SignOutProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    protected String getTopic() {
        return TOPIC;
    }
}

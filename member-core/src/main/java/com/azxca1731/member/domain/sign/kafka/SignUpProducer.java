package com.azxca1731.member.domain.sign.kafka;

import com.azxca1731.member.common.KafkaMessageProducerTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SignUpProducer extends KafkaMessageProducerTemplate<SignUpTopicMessage> {
    private final static String TOPIC = "member.sign.up.1";

    public SignUpProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    protected String getTopic() {
        return TOPIC;
    }
}

package com.azxca1731.member;

import com.azxca1731.member.configuration.KafkaConsumerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({DomainConfiguration.class, KafkaConsumerConfiguration.class})
@SpringBootApplication
public class MemberMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberMessageApplication.class, args);
    }
}

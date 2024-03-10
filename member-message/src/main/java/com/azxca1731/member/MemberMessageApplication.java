package com.azxca1731.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(DomainConfiguration.class)
@SpringBootApplication
public class MemberMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberMessageApplication.class, args);
    }
}

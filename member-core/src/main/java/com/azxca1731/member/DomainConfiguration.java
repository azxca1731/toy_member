package com.azxca1731.member;

import com.azxca1731.member.config.KafkaConfiguration;
import com.azxca1731.member.security.SecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableJpaAuditing
@Import({KafkaConfiguration.class, SecurityConfiguration.class})
public class DomainConfiguration {
}

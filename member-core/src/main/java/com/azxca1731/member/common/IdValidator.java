package com.azxca1731.member.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IdValidator {
    public boolean isValidated(String idString) {
        long id;
        try {
            id = Long.parseLong(idString);
        } catch (NumberFormatException nfe) {
            log.info("user request wrong member id: {}", idString);
            return false;
        }

        if (id <= 0) {
            log.info("user request wrong member id: {}", id);
            return false;
        }

        return true;
    }
}

package com.azxca1731.member.testcontroller;

import com.azxca1731.member.domain.pii.RetentionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConsumeController {
    private final RetentionService retentionService;

    @GetMapping(TestUrls.RETENTION)
    public long resendSignUp(@PathVariable String memberId) {
        return retentionService.migrateUserInfo(memberId);
    }
}

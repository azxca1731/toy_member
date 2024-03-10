package com.azxca1731.member.domain.verify;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifyResponse {
    private final long memberId;
    private final String name;
    private final String mobile;
    private final String birthday;
    private final String rrn7th;
    private final String key;
}

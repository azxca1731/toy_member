package com.azxca1731.member.domain.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnrollMemberCreateRequest {
    private String name;
    private String mobile;
    private String birthday;
    private String rrn7th;
    private String provider;
}

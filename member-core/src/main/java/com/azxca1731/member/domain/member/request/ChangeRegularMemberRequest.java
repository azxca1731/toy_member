package com.azxca1731.member.domain.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(exclude = "rawPassword")
public class ChangeRegularMemberRequest {
    private long memberId;
    private String email;
    private String rawPassword;
}

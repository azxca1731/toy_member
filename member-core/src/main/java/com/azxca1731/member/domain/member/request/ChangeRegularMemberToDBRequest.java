package com.azxca1731.member.domain.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(exclude = "password")
public class ChangeRegularMemberToDBRequest {
    private long memberId;
    private String email;
    private String password;
}

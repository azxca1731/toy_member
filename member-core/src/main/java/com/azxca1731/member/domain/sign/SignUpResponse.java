package com.azxca1731.member.domain.sign;

import com.azxca1731.member.domain.business.BusinessResponse;
import com.azxca1731.member.domain.member.MemberResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponse {
    private final MemberResponse memberResponse;
    private final BusinessResponse businessResponse;
}

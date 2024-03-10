package com.azxca1731.member.domain.sign.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String memberId;
    private String email;
    private String rawPassword;
    private String businessNumber;
    private String businessName;
    private String zipCode;
    private String roadAddress;
    private String roadAddressDetail;
    private String key;
}

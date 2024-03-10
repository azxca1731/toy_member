package com.azxca1731.member.domain.sign.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SingUpRequest {
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

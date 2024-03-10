package com.azxca1731.member.domain.business;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BusinessCreateRequest {
    private long ownerMemberId;
    private String businessNumber;
    private String businessName;
    private String zipCode;
    private String roadAddress;
    private String roadAddressDetail;
}

package com.azxca1731.member.domain.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessResponse {
    private static final long INVALID_ID = -1;

    private long businessId;
    private long ownerMemberId;
    private String businessNumber;
    private String businessName;
    private String zipCode;
    private String roadAddress;
    private String roadAddressDetail;
    private Boolean isMain;

    public static BusinessResponse of(Business business) {
        return BusinessResponse.builder()
                .businessId(business.getBusinessId())
                .ownerMemberId(business.getOwnerMemberId())
                .businessNumber(business.getBusinessNumber())
                .businessName(business.getBusinessName())
                .zipCode(business.getZipCode())
                .roadAddress(business.getRoadAddress())
                .roadAddressDetail(business.getRoadAddressDetail())
                .isMain(business.getIsMain())
                .build();
    }

    public static BusinessResponse empty() {
        return BusinessResponse.builder()
                .businessId(INVALID_ID)
                .build();
    }
}

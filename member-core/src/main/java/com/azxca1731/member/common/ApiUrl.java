package com.azxca1731.member.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiUrl {
    private static final String ROOT = "/api";
    private static final String V1 = "/v1";

    // Member
    public static final String MEMBER_PREFIX = "/member";
    public static final String MEMBER = ROOT + V1 + MEMBER_PREFIX;
    public static final String MEMBER_DETAIL = ROOT + V1 + MEMBER_PREFIX + "/{memberId}";

    // Business
    public static final String BUSINESS_PREFIX = "/business";
    public static final String BUSINESS_DETAIL = ROOT + V1 + BUSINESS_PREFIX + "/{businessId}";
    // Sign Up
    public static final String SIGN_UP = ROOT + V1 + "/sign-up";
    // Verification
    public static final String VERIFY = ROOT + V1 + "/verify";
    // Sign Out
    public static final String SIGN_OUT = ROOT + V1 + "/sign-out";
}

package com.azxca1731.member.testcontroller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUrls {
    public static final String SIGN_UP = "sign-up";
    public static final String SIGN_OUT = "sign-out";
    public static final String RETENTION = "consume-retention/{memberId}";
}

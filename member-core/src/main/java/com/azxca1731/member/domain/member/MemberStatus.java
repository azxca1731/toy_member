package com.azxca1731.member.domain.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberStatus {
    ENROLLED("인증을 마치고 가입을 안한 상태", 5, "member.createdAt"),
    REGULAR("정상 이용 상태", 0,""),
    WITHDRAW("탈퇴를 한 상태", 90, "member.withdrawAt");

    private final String description;

    // if 0, it will be non deleted target
    private final int deletedPeriodAfterLastDay;
    // if empty, it will be non deleted target
    private final String periodTargetColumnName;
}

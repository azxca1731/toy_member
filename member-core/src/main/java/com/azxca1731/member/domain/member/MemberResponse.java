package com.azxca1731.member.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private static final long INVALID_ID = -1;
    private long memberId;
    private String email;
    private String name;
    private String mobile;
    private String birthday;
    private String rrn7th;
    private MemberStatus status;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .name(member.getName())
                .mobile(member.getMobile())
                .birthday(member.getBirthday())
                .rrn7th(member.getRrn7th())
                .status(member.getMemberStatus())
                .build();
    }

    public static MemberResponse empty() {
        return MemberResponse.builder()
                .memberId(INVALID_ID)
                .build();
    }
}

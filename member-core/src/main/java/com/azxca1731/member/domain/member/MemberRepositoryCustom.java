package com.azxca1731.member.domain.member;

import com.azxca1731.member.domain.member.request.ChangeRegularMemberToDBRequest;

public interface MemberRepositoryCustom {
    long changeToRegularMember(ChangeRegularMemberToDBRequest request);

    long changeToWithdrawn(String memberId);
}

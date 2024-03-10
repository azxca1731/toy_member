package com.azxca1731.member.controller;

import com.azxca1731.member.common.ApiUrl;
import com.azxca1731.member.domain.member.request.EnrollMemberCreateRequest;
import com.azxca1731.member.domain.member.MemberResponse;
import com.azxca1731.member.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping(ApiUrl.MEMBER)
    public MemberResponse createMember(@RequestBody EnrollMemberCreateRequest request) {
        return memberService.createEnrollMember(request);
    }

    @GetMapping(ApiUrl.MEMBER_DETAIL)
    public MemberResponse getMember(@PathVariable String memberId) {
        return memberService.findByMemberId(memberId);
    }

}

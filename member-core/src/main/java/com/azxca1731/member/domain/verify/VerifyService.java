package com.azxca1731.member.domain.verify;

import com.azxca1731.member.common.BaseResponse;
import com.azxca1731.member.common.Code;
import com.azxca1731.member.domain.member.request.EnrollMemberCreateRequest;
import com.azxca1731.member.domain.member.MemberResponse;
import com.azxca1731.member.domain.member.MemberService;
import com.azxca1731.member.security.Md5HashEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VerifyService {
    private final MemberService memberService;
    private final VerifyProvider verifyProvider;
    private final VerifyValidator validator;
    private final Md5HashEncoder md5HashEncoder;

    private static final String VERIFY_REFUSE_MESSAGE = "정확하지 않은 정보입니다. 확인해주세요";

    public BaseResponse verify(VerifyRequest request) {
        List<String> unValidField = validator.verifyRequest(request);
        if (!unValidField.isEmpty()) {
            return BaseResponse.failed(unValidField, Code.VERIFY_VALIDATION_REJECT, VERIFY_REFUSE_MESSAGE);
        }

        MemberResponse memberResponse = memberService.createEnrollMember(createRequest(request));

        boolean verified = verifyProvider.verify(request);

        String key = md5HashEncoder.encode(String.valueOf(memberResponse.getMemberId()));

        if (!verified) {
            return BaseResponse.failed(Code.VERIFY_PROVIDER_REJECT, VERIFY_REFUSE_MESSAGE);
        }

        return BaseResponse.success(makeResponse(memberResponse, key), Code.VERIFY_SUCCESS);
    }

    private VerifyResponse makeResponse(MemberResponse member, String key) {
        return VerifyResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .mobile(member.getMobile())
                .birthday(member.getBirthday())
                .rrn7th(member.getRrn7th())
                .key(key)
                .build();
    }

    private EnrollMemberCreateRequest createRequest(VerifyRequest request) {
        return EnrollMemberCreateRequest.builder()
                .name(request.getName())
                .mobile(request.getMobile())
                .birthday(request.getBirthday())
                .rrn7th(request.getRrn7th())
                .provider(request.getProvider())
                .build();
    }
}

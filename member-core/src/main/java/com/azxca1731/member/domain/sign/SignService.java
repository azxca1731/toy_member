package com.azxca1731.member.domain.sign;

import com.azxca1731.member.common.BaseResponse;
import com.azxca1731.member.common.Code;
import com.azxca1731.member.domain.business.BusinessCreateRequest;
import com.azxca1731.member.domain.business.BusinessResponse;
import com.azxca1731.member.domain.business.BusinessService;
import com.azxca1731.member.domain.member.MemberResponse;
import com.azxca1731.member.domain.member.MemberService;
import com.azxca1731.member.domain.member.request.ChangeRegularMemberRequest;
import com.azxca1731.member.domain.sign.kafka.SignOutProducer;
import com.azxca1731.member.domain.sign.kafka.SignOutTopicMessage;
import com.azxca1731.member.domain.sign.kafka.SignUpProducer;
import com.azxca1731.member.domain.sign.kafka.SignUpTopicMessage;
import com.azxca1731.member.domain.sign.request.SingOutRequest;
import com.azxca1731.member.domain.sign.request.SingUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SignService {
    private final MemberService memberService;
    private final BusinessService businessService;
    private final SignUpValidator validator;
    private final SignUpProducer signUpProducer;
    private final SignOutProducer signOutProducer;
    private static final String SIGN_UP_REFUSE_MESSAGE = "정확하지 않은 정보입니다. 확인해주세요";
    private static final String SIGN_OUT_REFUSE_MESSAGE = "유효하지 않은 멤버입니다. 확인해주세요";

    @Transactional
    public BaseResponse signUp(SingUpRequest request) {
        List<String> unValidField = validator.verifyRequest(request);
        if (!unValidField.isEmpty()) {
            return BaseResponse.failed(unValidField, Code.SIGN_UP_VALIDATION_REJECT, SIGN_UP_REFUSE_MESSAGE);
        }

        MemberResponse member = memberService.changeRegularFromEnroll(makeMemberRequest(request));
        BusinessResponse business = businessService.createBusiness(makeBusinessRequest(request, member));

        checkResponse(member, business);

        signUpProducer.send(makeKafkaMessage(member, business));

        return BaseResponse.success(SignUpResponse.builder()
                .businessResponse(business)
                .memberResponse(member)
                .build(), Code.SIGN_UP_SUCCESS);
    }

    public BaseResponse signOut(SingOutRequest request) {
        long changed = memberService.changeToWithdrawn(request.getMemberId());
        if (changed == 0) {
            return BaseResponse.failed(Code.SIGN_OUT_NOT_CHANGED_REJECT, SIGN_OUT_REFUSE_MESSAGE);
        }
        signOutProducer.send(makeKafkaMessage(request));

        return BaseResponse.success(Code.SIGN_OUT_SUCCESS);
    }

    private void checkResponse(MemberResponse member, BusinessResponse business) {
        if (Objects.isNull(member)) {
            throw new RuntimeException("member can't be null");
        }

        if (Objects.isNull(business)) {
            throw new RuntimeException("business can't be null");
        }

        if (member.getMemberId() <= 0) {
            throw new RuntimeException("invalid member id, member id: " + member.getMemberId());
        }

        if (business.getBusinessId() <= 0) {
            throw new RuntimeException("invalid business id, business id: " + business.getBusinessId());
        }
    }

    private SignUpTopicMessage makeKafkaMessage(MemberResponse member, BusinessResponse business) {
        return SignUpTopicMessage.builder()
                .memberId(member.getMemberId())
                .businessId(business.getBusinessId())
                .build();
    }

    private SignOutTopicMessage makeKafkaMessage(SingOutRequest request) {
        return SignOutTopicMessage.builder()
                .memberId(Long.parseLong(request.getMemberId()))
                .build();
    }

    private ChangeRegularMemberRequest makeMemberRequest(SingUpRequest request) {
        return ChangeRegularMemberRequest.builder()
                .memberId(Long.parseLong(request.getMemberId()))
                .rawPassword(request.getRawPassword())
                .email(request.getEmail())
                .build();
    }

    private BusinessCreateRequest makeBusinessRequest(SingUpRequest request, MemberResponse member) {
        return BusinessCreateRequest.builder()
                .ownerMemberId(member.getMemberId())
                .businessNumber(request.getBusinessNumber())
                .businessName(request.getBusinessName())
                .zipCode(request.getZipCode())
                .roadAddress(request.getRoadAddress())
                .roadAddressDetail(request.getRoadAddressDetail())
                .build();
    }
}

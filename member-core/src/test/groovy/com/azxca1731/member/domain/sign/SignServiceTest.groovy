package com.azxca1731.member.domain.sign

import com.azxca1731.member.common.BaseResponse
import com.azxca1731.member.common.Code
import com.azxca1731.member.common.exception.NotSavedException
import com.azxca1731.member.domain.business.BusinessResponse
import com.azxca1731.member.domain.business.BusinessService
import com.azxca1731.member.domain.member.MemberResponse
import com.azxca1731.member.domain.member.MemberService
import com.azxca1731.member.domain.sign.kafka.SignOutProducer
import com.azxca1731.member.domain.sign.kafka.SignUpProducer
import com.azxca1731.member.domain.sign.request.SignUpRequest
import com.azxca1731.member.domain.sign.request.SingOutRequest
import spock.lang.Specification
import spock.lang.Subject

class SignServiceTest extends Specification {
    @Subject
    SignService sut

    def memberService = Mock(MemberService)
    def businessService = Mock(BusinessService)
    def validator = Mock(SignUpRequestValidator)
    def signUpProducer = Mock(SignUpProducer)
    def signOutProducer = Mock(SignOutProducer)

    def MOCK_MEMBER_ID = "1"
    def MOCK_BUSINESS_ID = "10"
    def MOCK_EMAIL = "azxca1731@gmail.com"
    def MOCK_RAW_PASSWORD = "hello1"
    def MOCK_BUSINESS_NUMBER = "1239381232"
    def MOCK_BUSINESS_NAME = "이가네 통닭"
    def MOCK_ZIP_CODE = "08173"
    def MOCK_ROAD_ADDRESS = "경기도 수원시 영통구 매영로 10"
    def MOCK_ROAD_ADDRESS_DETAIL = "6동 903호"
    def MOCK_KEY = "c4ca4238a0b923820dcc509a6f75849b"

    def setup() {
        sut = new SignService(
                memberService,
                businessService,
                validator,
                signUpProducer,
                signOutProducer
        )
    }

    def 'signUp - works well'() {
        given:
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .memberId(MOCK_MEMBER_ID)
                .email(MOCK_EMAIL)
                .rawPassword(MOCK_RAW_PASSWORD)
                .businessNumber(MOCK_BUSINESS_NUMBER)
                .businessName(MOCK_BUSINESS_NAME)
                .zipCode(MOCK_ZIP_CODE)
                .roadAddress(MOCK_ROAD_ADDRESS)
                .roadAddressDetail(MOCK_ROAD_ADDRESS_DETAIL)
                .key(MOCK_KEY)
                .build()
        def mockMember = Mock(MemberResponse)
        mockMember.getMemberId() >> Long.parseLong(MOCK_MEMBER_ID)
        def mockBusiness = Mock(BusinessResponse)
        mockBusiness.getBusinessId() >> Long.parseLong(MOCK_BUSINESS_ID)

        1 * validator.verifyRequest(*_) >> []
        1 * memberService.changeRegularFromEnroll(*_) >> mockMember
        1 * businessService.createBusiness(*_) >> mockBusiness
        1 * signUpProducer.send(*_)

        when:
        def result = sut.signUp(signUpRequest)

        then:
        result
        result.code == Code.SIGN_UP_SUCCESS
    }

    def 'signUp - member save not work'() {
        given:
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .memberId(MOCK_MEMBER_ID)
                .email(MOCK_EMAIL)
                .rawPassword(MOCK_RAW_PASSWORD)
                .businessNumber(MOCK_BUSINESS_NUMBER)
                .businessName(MOCK_BUSINESS_NAME)
                .zipCode(MOCK_ZIP_CODE)
                .roadAddress(MOCK_ROAD_ADDRESS)
                .roadAddressDetail(MOCK_ROAD_ADDRESS_DETAIL)
                .key(MOCK_KEY)
                .build()
        def mockMember = Mock(MemberResponse)
        def mockBusiness = Mock(BusinessResponse)
        mockMember.getMemberId() >> 0

        1 * validator.verifyRequest(*_) >> []
        1 * memberService.changeRegularFromEnroll(*_) >> mockMember
        1 * businessService.createBusiness(*_) >> mockBusiness
        0 * signUpProducer.send(*_)

        when:
        sut.signUp(signUpRequest)

        then:
        thrown(NotSavedException)
    }

    def 'signUp - business not work'() {
        given:
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .memberId(MOCK_MEMBER_ID)
                .email(MOCK_EMAIL)
                .rawPassword(MOCK_RAW_PASSWORD)
                .businessNumber(MOCK_BUSINESS_NUMBER)
                .businessName(MOCK_BUSINESS_NAME)
                .zipCode(MOCK_ZIP_CODE)
                .roadAddress(MOCK_ROAD_ADDRESS)
                .roadAddressDetail(MOCK_ROAD_ADDRESS_DETAIL)
                .key(MOCK_KEY)
                .build()
        def mockMember = Mock(MemberResponse)
        def mockBusiness = Mock(BusinessResponse)
        mockMember.getMemberId() >> Long.parseLong(MOCK_MEMBER_ID)
        mockBusiness.getBusinessId() >> 0

        1 * validator.verifyRequest(*_) >> []
        1 * memberService.changeRegularFromEnroll(*_) >> mockMember
        1 * businessService.createBusiness(*_) >> mockBusiness
        0 * signUpProducer.send(*_)

        when:
        sut.signUp(signUpRequest)

        then:
        thrown(NotSavedException)
    }

    def 'signUp - validator got error'() {
        given:
        SignUpRequest signUpRequest = SignUpRequest.builder().build()

        1 * validator.verifyRequest(*_) >> ["member"]
        0 * memberService.changeRegularFromEnroll(*_)
        0 * businessService.createBusiness(*_)
        0 * signUpProducer.send(*_)

        when:
        def result = sut.signUp(signUpRequest)

        then:
        result
        result.getCode() == Code.SIGN_UP_VALIDATION_REJECT
    }

    def 'signOut - works well'() {
        given:
        def request = SingOutRequest.builder()
                .memberId(MOCK_MEMBER_ID)
                .build()
        1 * memberService.changeToWithdrawn(MOCK_MEMBER_ID) >> 1
        1 * signOutProducer.send(*_)
        when:
        BaseResponse result = sut.signOut(request)

        then:
        result.getCode() == Code.SIGN_OUT_SUCCESS
    }

    def 'signOut - not changed'() {
        given:
        def request = SingOutRequest.builder()
                .memberId(MOCK_MEMBER_ID)
                .build()
        1 * memberService.changeToWithdrawn(MOCK_MEMBER_ID) >> 0
        0 * signOutProducer.send(*_)
        when:
        BaseResponse result = sut.signOut(request)

        then:
        result.getCode() == Code.SIGN_OUT_NOT_CHANGED_REJECT
    }
}

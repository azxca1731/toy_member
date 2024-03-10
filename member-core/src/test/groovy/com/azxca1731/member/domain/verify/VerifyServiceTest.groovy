package com.azxca1731.member.domain.verify

import com.azxca1731.member.common.Code
import com.azxca1731.member.domain.member.MemberResponse
import com.azxca1731.member.domain.member.MemberService
import com.azxca1731.member.domain.member.request.EnrollMemberCreateRequest
import com.azxca1731.member.security.Md5HashEncoder
import spock.lang.Specification
import spock.lang.Subject

class VerifyServiceTest extends Specification {
    @Subject
    VerifyService sut
    def memberService = Mock(MemberService)
    def verifyProvider = Mock(VerifyProvider)
    def validator = Mock(VerifyRequestValidator)
    def md5HashEncoder = Mock(Md5HashEncoder)

    private static MOCK_NAME = "이정훈"
    private static MOCK_MOBILE = "01030921704"
    private static MOCK_BIRTHDAY = "19950302"
    private static MOCK_RRN7TH = "1"
    private static MOCK_PROVIDER = "KTMOBILE"
    private static MOCK_MEMBER_ID = 1L
    private static MOCK_KEY = "fdsfsuifkln34io"

    def setup() {
        sut = new VerifyService(memberService,
                verifyProvider,
                validator,
                md5HashEncoder)
    }

    def 'verify - works well'() {
        given:
        def mock = VerifyRequest.builder()
                .name(MOCK_NAME)
                .mobile(MOCK_MOBILE)
                .birthday(MOCK_BIRTHDAY)
                .rrn7th(MOCK_RRN7TH)
                .provider(MOCK_PROVIDER)
                .build()
        def memberMock = Mock(MemberResponse)

        memberMock.getMemberId() >> MOCK_MEMBER_ID
        memberMock.getName() >> MOCK_NAME
        memberMock.getMobile() >> MOCK_MOBILE
        memberMock.getBirthday() >> MOCK_BIRTHDAY
        memberMock.getRrn7th() >> MOCK_RRN7TH
        1 * validator.verifyRequest(*_) >> []
        1 * memberService.createEnrollMember({ EnrollMemberCreateRequest it -> it.name == MOCK_NAME } as EnrollMemberCreateRequest) >> memberMock
        1 * verifyProvider.verify(*_) >> true
        1 * md5HashEncoder.encode(*_) >> MOCK_KEY

        when:
        def result = sut.verify(mock)

        then:
        result
        result.code == Code.VERIFY_SUCCESS
        result.data
    }

    def 'verify - fail by provider'() {
        given:
        def mock = VerifyRequest.builder()
                .name(MOCK_NAME)
                .mobile(MOCK_MOBILE)
                .birthday(MOCK_BIRTHDAY)
                .rrn7th(MOCK_RRN7TH)
                .provider(MOCK_PROVIDER)
                .build()
        def memberMock = Mock(MemberResponse)

        memberMock.getMemberId() >> MOCK_MEMBER_ID
        memberMock.getName() >> MOCK_NAME
        memberMock.getMobile() >> MOCK_MOBILE
        memberMock.getBirthday() >> MOCK_BIRTHDAY
        memberMock.getRrn7th() >> MOCK_RRN7TH
        1 * validator.verifyRequest(*_) >> []
        1 * memberService.createEnrollMember({ EnrollMemberCreateRequest it -> it.name == MOCK_NAME } as EnrollMemberCreateRequest) >> memberMock
        1 * verifyProvider.verify(*_) >> false
        1 * md5HashEncoder.encode(*_) >> MOCK_KEY

        when:
        def result = sut.verify(mock)

        then:
        result
        result.code == Code.VERIFY_PROVIDER_REJECT
    }

    def 'verify - fail by validator'() {
        given:
        def mock = VerifyRequest.builder()
                .build()

        1 * validator.verifyRequest(*_) >> ["memberId"]
        0 * memberService.createEnrollMember(*_)
        0 * verifyProvider.verify(*_) >> false
        0 * md5HashEncoder.encode(*_) >> MOCK_KEY

        when:
        def result = sut.verify(mock)

        then:
        result
        result.code == Code.VERIFY_VALIDATION_REJECT
    }
}

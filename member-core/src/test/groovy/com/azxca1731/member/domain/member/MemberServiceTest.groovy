package com.azxca1731.member.domain.member

import com.azxca1731.member.common.IdValidator
import com.azxca1731.member.domain.member.request.ChangeRegularMemberRequest
import com.azxca1731.member.domain.member.request.EnrollMemberCreateRequest
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Subject

class MemberServiceTest extends Specification {
    @Subject
    MemberService sut
    def idValidator = Mock(IdValidator)
    def memberRepository = Mock(MemberRepository)
    def passwordEncoder = Mock(PasswordEncoder)

    def MOCK_EMAIL = "azxca1731@gmail.com"
    def MOCK_MEMBER_ID = "1"
    def MOCK_NAME = "이정훈"
    def MOCK_MOBILE = "01030921704"
    def MOCK_BIRTHDAY = "19950302"
    def MOCK_RRN7TH = "1"
    def MOCK_PROVIDER = "KTMOBILE"
    def MOCK_RAW_PASSWORD = "hello1"
    def MOCK_PASSWORD = "df89213njsdloidsf"

    def setup() {
        sut = new MemberService(
                idValidator,
                memberRepository,
                passwordEncoder
        )
    }

    def 'findByMemberId - works well'() {
        given:
        def mockMember = Mock(Member)
        mockMember.getMemberId() >> Long.parseLong(MOCK_MEMBER_ID)
        mockMember.getEmail() >> MOCK_EMAIL
        mockMember.getName() >> MOCK_NAME
        mockMember.getMobile() >> MOCK_MOBILE
        mockMember.getBirthday() >> MOCK_BIRTHDAY
        mockMember.getRrn7th() >> MOCK_RRN7TH
        mockMember.getMemberStatus() >> MemberStatus.REGULAR

        1 * idValidator.isValidated(MOCK_MEMBER_ID) >> true
        1 * memberRepository.findById(Long.parseLong(MOCK_MEMBER_ID)) >> Optional.of(mockMember)

        when:
        def result = sut.findByMemberId(MOCK_MEMBER_ID)

        then:
        result
        result.name == MOCK_NAME
    }

    def 'findByMemberId - id validator return false'() {
        given:
        1 * idValidator.isValidated(MOCK_MEMBER_ID) >> false
        0 * memberRepository.findById(Long.parseLong(MOCK_MEMBER_ID))

        when:
        def result = sut.findByMemberId(MOCK_MEMBER_ID)

        then:
        result
        result.memberId == -1
    }

    def 'changeToWithdrawn - works well'() {
        given:
        1 * idValidator.isValidated(MOCK_MEMBER_ID) >> true
        1 * memberRepository.changeToWithdrawn(MOCK_MEMBER_ID) >> 1

        when:
        def result = sut.changeToWithdrawn(MOCK_MEMBER_ID)

        then:
        result == 1
    }

    def 'changeToWithdrawn - id validator return false'() {
        given:
        1 * idValidator.isValidated(MOCK_MEMBER_ID) >> false
        0 * memberRepository.changeToWithdrawn(MOCK_MEMBER_ID)

        when:
        def result = sut.changeToWithdrawn(MOCK_MEMBER_ID)

        then:
        result == 0
    }

    def 'createEnrollMember - works well'() {
        given:
        def request = EnrollMemberCreateRequest.builder()
                .birthday(MOCK_BIRTHDAY)
                .mobile(MOCK_MOBILE)
                .name(MOCK_NAME)
                .rrn7th(MOCK_RRN7TH)
                .provider(MOCK_PROVIDER)
                .build()
        def mockMember = Mock(Member)
        mockMember.getMemberId() >> Long.parseLong(MOCK_MEMBER_ID)
        mockMember.getEmail() >> MOCK_EMAIL
        mockMember.getName() >> MOCK_NAME
        mockMember.getMobile() >> MOCK_MOBILE
        mockMember.getBirthday() >> MOCK_BIRTHDAY
        mockMember.getRrn7th() >> MOCK_RRN7TH
        mockMember.getMemberStatus() >> MemberStatus.ENROLLED
        1 * memberRepository.save({ it -> it.memberStatus == MemberStatus.ENROLLED }) >> mockMember

        when:
        def result = sut.createEnrollMember(request)

        then:
        result
    }

    def 'createEnrollMember - save exception'() {
        given:
        def request = EnrollMemberCreateRequest.builder()
                .birthday(MOCK_BIRTHDAY)
                .mobile(MOCK_MOBILE)
                .name(MOCK_NAME)
                .rrn7th(MOCK_RRN7TH)
                .provider(MOCK_PROVIDER)
                .build()
        def mockMember = Mock(Member)
        mockMember.getMemberId() >> Long.parseLong(MOCK_MEMBER_ID)
        mockMember.getEmail() >> MOCK_EMAIL
        mockMember.getName() >> MOCK_NAME
        mockMember.getMobile() >> MOCK_MOBILE
        mockMember.getBirthday() >> MOCK_BIRTHDAY
        mockMember.getRrn7th() >> MOCK_RRN7TH
        mockMember.getMemberStatus() >> MemberStatus.ENROLLED
        1 * memberRepository.save({ it -> it.memberStatus == MemberStatus.ENROLLED }) >> { throw new RuntimeException() }

        when:
        def result = sut.createEnrollMember(request)

        then:
        result
        result.memberId == -1
    }

    def 'changeRegularFromEnroll - works well'() {
        given:
        ChangeRegularMemberRequest request = ChangeRegularMemberRequest.builder()
                .memberId(Long.parseLong(MOCK_MEMBER_ID))
                .email(MOCK_EMAIL)
                .rawPassword(MOCK_RAW_PASSWORD)
                .build()
        def mockMember = Mock(Member)
        mockMember.getMemberId() >> Long.parseLong(MOCK_MEMBER_ID)
        mockMember.getEmail() >> MOCK_EMAIL
        mockMember.getName() >> MOCK_NAME
        mockMember.getMobile() >> MOCK_MOBILE
        mockMember.getBirthday() >> MOCK_BIRTHDAY
        mockMember.getRrn7th() >> MOCK_RRN7TH
        mockMember.getMemberStatus() >> MemberStatus.REGULAR

        1 * memberRepository.existsById(Long.parseLong(MOCK_MEMBER_ID)) >> true
        1 * passwordEncoder.encode(MOCK_RAW_PASSWORD) >> MOCK_PASSWORD

        1 * memberRepository.changeToRegularMember({it -> it.password == MOCK_PASSWORD}) >> 1
        1 * memberRepository.findById(Long.parseLong(MOCK_MEMBER_ID)) >> Optional.of(mockMember)
        when:
        def result = sut.changeRegularFromEnroll(request)

        then:
        result
        result.memberId == Long.parseLong(MOCK_MEMBER_ID)
    }

    def 'changeRegularFromEnroll - saved not exist'() {
        given:
        ChangeRegularMemberRequest request = ChangeRegularMemberRequest.builder()
                .memberId(Long.parseLong(MOCK_MEMBER_ID))
                .email(MOCK_EMAIL)
                .rawPassword(MOCK_RAW_PASSWORD)
                .build()

        1 * memberRepository.existsById(Long.parseLong(MOCK_MEMBER_ID)) >> true
        1 * passwordEncoder.encode(MOCK_RAW_PASSWORD) >> MOCK_PASSWORD
        1 * memberRepository.changeToRegularMember({it -> it.password == MOCK_PASSWORD}) >> 1
        1 * memberRepository.findById(Long.parseLong(MOCK_MEMBER_ID)) >> Optional.ofNullable(null)

        when:
        def result = sut.changeRegularFromEnroll(request)

        then:
        result
        result.memberId == -1
    }

    def 'changeRegularFromEnroll - not changed'() {
        given:
        ChangeRegularMemberRequest request = ChangeRegularMemberRequest.builder()
                .memberId(Long.parseLong(MOCK_MEMBER_ID))
                .email(MOCK_EMAIL)
                .rawPassword(MOCK_RAW_PASSWORD)
                .build()
        def mockMember = Mock(Member)
        mockMember.getMemberId() >> Long.parseLong(MOCK_MEMBER_ID)
        mockMember.getEmail() >> MOCK_EMAIL
        mockMember.getName() >> MOCK_NAME
        mockMember.getMobile() >> MOCK_MOBILE
        mockMember.getBirthday() >> MOCK_BIRTHDAY
        mockMember.getRrn7th() >> MOCK_RRN7TH
        mockMember.getMemberStatus() >> MemberStatus.REGULAR

        1 * memberRepository.existsById(Long.parseLong(MOCK_MEMBER_ID)) >> true
        1 * passwordEncoder.encode(MOCK_RAW_PASSWORD) >> MOCK_PASSWORD
        1 * memberRepository.changeToRegularMember({it -> it.password == MOCK_PASSWORD}) >> 0
        0 * memberRepository.findById(*_)

        when:
        def result = sut.changeRegularFromEnroll(request)

        then:
        result
        result.memberId == -1
    }

    def 'changeRegularFromEnroll - not exist'() {
        given:
        ChangeRegularMemberRequest request = ChangeRegularMemberRequest.builder()
                .memberId(Long.parseLong(MOCK_MEMBER_ID))
                .email(MOCK_EMAIL)
                .rawPassword(MOCK_RAW_PASSWORD)
                .build()

        1 * memberRepository.existsById(Long.parseLong(MOCK_MEMBER_ID)) >> false
        0 * passwordEncoder.encode(*_)
        0 * memberRepository.changeToRegularMember(*_)
        0 * memberRepository.findById(*_)

        when:
        def result = sut.changeRegularFromEnroll(request)

        then:
        result
        result.memberId == -1
    }
}

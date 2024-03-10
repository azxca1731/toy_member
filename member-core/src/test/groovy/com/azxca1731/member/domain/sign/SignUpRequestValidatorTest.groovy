package com.azxca1731.member.domain.sign

import com.azxca1731.member.domain.sign.request.SignUpRequest
import com.azxca1731.member.security.Md5HashEncoder
import spock.lang.Specification
import spock.lang.Subject

class SignUpRequestValidatorTest extends Specification {
    @Subject
    SignUpRequestValidator sut
    def md5HashEncoder = Mock(Md5HashEncoder)

    def MOCK_MEMBER_ID = "1"
    def MOCK_EMAIL = "azxca1731@gmail.com"
    def MOCK_RAW_PASSWORD = "coupang1"
    def MOCK_BUSINESS_NUMBER = "1239381232"
    def MOCK_BUSINESS_NAME = "이가네 통닭"
    def MOCK_ZIP_CODE = "08173"
    def MOCK_ROAD_ADDRESS = "경기도 수원시 영통구 매영로 10"
    def MOCK_ROAD_ADDRESS_DETAIL = "6동 903호"
    def MOCK_KEY = "c4ca4238a0b923820dcc509a6f75849b"

    def setup() {
        sut = new SignUpRequestValidator(md5HashEncoder)
    }

    def 'verifyRequest - works well'() {
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
        1 * md5HashEncoder.matches(*_) >> true

        when:
        def result = sut.verifyRequest(signUpRequest)

        then:
        result.isEmpty()
    }

    def 'verifyRequest - validation fail - key'() {
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
        1 * md5HashEncoder.matches(*_) >> false

        when:
        def result = sut.verifyRequest(signUpRequest)

        then:
        result == ["key"]
    }

    def 'verifyRequest - validation fail - email'() {
        given:
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .memberId(MOCK_MEMBER_ID)
                .email(EMAIL)
                .rawPassword(MOCK_RAW_PASSWORD)
                .businessNumber(MOCK_BUSINESS_NUMBER)
                .businessName(MOCK_BUSINESS_NAME)
                .zipCode(MOCK_ZIP_CODE)
                .roadAddress(MOCK_ROAD_ADDRESS)
                .roadAddressDetail(MOCK_ROAD_ADDRESS_DETAIL)
                .key(MOCK_KEY)
                .build()
        1 * md5HashEncoder.matches(*_) >> true

        when:
        def result = sut.verifyRequest(signUpRequest)

        then:
        result == ["email"]

        where:
        EMAIL | ignored
        "azxca" | _
        "azxca@" | _
        "azxca@c.c" | _
    }

    def 'verifyRequest - validation fail - memberId'() {
        given:
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .memberId(MEMBER_ID)
                .email(MOCK_EMAIL)
                .rawPassword(MOCK_RAW_PASSWORD)
                .businessNumber(MOCK_BUSINESS_NUMBER)
                .businessName(MOCK_BUSINESS_NAME)
                .zipCode(MOCK_ZIP_CODE)
                .roadAddress(MOCK_ROAD_ADDRESS)
                .roadAddressDetail(MOCK_ROAD_ADDRESS_DETAIL)
                .key(MOCK_KEY)
                .build()
        1 * md5HashEncoder.matches(*_) >> true

        when:
        def result = sut.verifyRequest(signUpRequest)

        then:
        result == ["memberId"]

        where:
        MEMBER_ID | ignored
        "a" | _
        "-1" | _
    }

    def 'verifyRequest - validation fail - rawPassword'() {
        given:
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .memberId(MOCK_MEMBER_ID)
                .email(MOCK_EMAIL)
                .rawPassword(RAW_PASSWORD)
                .businessNumber(MOCK_BUSINESS_NUMBER)
                .businessName(MOCK_BUSINESS_NAME)
                .zipCode(MOCK_ZIP_CODE)
                .roadAddress(MOCK_ROAD_ADDRESS)
                .roadAddressDetail(MOCK_ROAD_ADDRESS_DETAIL)
                .key(MOCK_KEY)
                .build()
        1 * md5HashEncoder.matches(*_) >> true

        when:
        def result = sut.verifyRequest(signUpRequest)

        then:
        result == ["rawPassword"]

        where:
        RAW_PASSWORD | ignored
        "Supercalifragilisticexpialidocious" | _
        "super" | _
    }

    def 'verifyRequest - validation fail - businessNumber'() {
        given:
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .memberId(MOCK_MEMBER_ID)
                .email(MOCK_EMAIL)
                .rawPassword(MOCK_RAW_PASSWORD)
                .businessNumber(BUSINESS_NUMBER)
                .businessName(MOCK_BUSINESS_NAME)
                .zipCode(MOCK_ZIP_CODE)
                .roadAddress(MOCK_ROAD_ADDRESS)
                .roadAddressDetail(MOCK_ROAD_ADDRESS_DETAIL)
                .key(MOCK_KEY)
                .build()
        1 * md5HashEncoder.matches(*_) >> true

        when:
        def result = sut.verifyRequest(signUpRequest)

        then:
        result == ["businessNumber"]

        where:
        BUSINESS_NUMBER | ignored
        "12345678901" | _
        "123456789" | _
        "-123456789" | _
        "super" | _
    }

    def 'verifyRequest - validation fail - businessName'() {
        given:
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .memberId(MOCK_MEMBER_ID)
                .email(MOCK_EMAIL)
                .rawPassword(MOCK_RAW_PASSWORD)
                .businessNumber(MOCK_BUSINESS_NUMBER)
                .businessName(BUSINESS_NAME)
                .zipCode(MOCK_ZIP_CODE)
                .roadAddress(MOCK_ROAD_ADDRESS)
                .roadAddressDetail(MOCK_ROAD_ADDRESS_DETAIL)
                .key(MOCK_KEY)
                .build()
        1 * md5HashEncoder.matches(*_) >> true

        when:
        def result = sut.verifyRequest(signUpRequest)

        then:
        result == ["businessName"]

        where:
        BUSINESS_NAME | ignored
        "" | _
    }

    def 'verifyRequest - validation fail - zipCode'() {
        given:
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .memberId(MOCK_MEMBER_ID)
                .email(MOCK_EMAIL)
                .rawPassword(MOCK_RAW_PASSWORD)
                .businessNumber(MOCK_BUSINESS_NUMBER)
                .businessName(MOCK_BUSINESS_NAME)
                .zipCode(ZIP_CODE)
                .roadAddress(MOCK_ROAD_ADDRESS)
                .roadAddressDetail(MOCK_ROAD_ADDRESS_DETAIL)
                .key(MOCK_KEY)
                .build()
        1 * md5HashEncoder.matches(*_) >> true

        when:
        def result = sut.verifyRequest(signUpRequest)

        then:
        result == ["zipCode"]

        where:
        ZIP_CODE | ignored
        "" | _
        "123456" | _
        "a" | _
    }
}

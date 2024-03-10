package com.azxca1731.member.domain.verify

import spock.lang.Specification
import spock.lang.Subject

class VerifyRequestValidatorTest extends Specification {
    @Subject
    VerifyRequestValidator sut

    private static MOCK_NAME = "이정훈"
    private static MOCK_MOBILE = "01030921704"
    private static MOCK_BIRTHDAY = "19950302"
    private static MOCK_RRN7TH = "1"
    private static MOCK_PROVIDER = "KTMOBILE"

    def setup() {
        sut = new VerifyRequestValidator()
    }

    def 'verifyRequest - works well'() {
        given:
        def mock = VerifyRequest.builder()
                .name(MOCK_NAME)
                .mobile(MOCK_MOBILE)
                .birthday(MOCK_BIRTHDAY)
                .rrn7th(MOCK_RRN7TH)
                .provider(MOCK_PROVIDER)
                .build()

        when:
        def result = sut.verifyRequest(mock)

        then:
        result.isEmpty()
    }

    def 'verifyRequest - name'() {
        given:
        def mock = VerifyRequest.builder()
                .name(NAME)
                .mobile(MOCK_MOBILE)
                .birthday(MOCK_BIRTHDAY)
                .rrn7th(MOCK_RRN7TH)
                .provider(MOCK_PROVIDER)
                .build()

        when:
        def result = sut.verifyRequest(mock)

        then:
        result == ["name"]

        where:
        NAME                      | ignored
        "이"                       | _
        "책상위에사과두개와바나나하나와딸기한알이있었다" | _
    }

    def 'verifyRequest - rrn7th'() {
        given:
        def mock = VerifyRequest.builder()
                .name(MOCK_NAME)
                .mobile(MOCK_MOBILE)
                .birthday(MOCK_BIRTHDAY)
                .rrn7th(RRN7TH)
                .provider(MOCK_PROVIDER)
                .build()

        when:
        def result = sut.verifyRequest(mock)

        then:
        result == ["rrn7th"]

        where:
        RRN7TH | ignored
        "20"   | _
        "이"    | _
    }

    def 'verifyRequest - birthday'() {
        given:
        def mock = VerifyRequest.builder()
                .name(MOCK_NAME)
                .mobile(MOCK_MOBILE)
                .birthday(BIRTHDAY)
                .rrn7th(MOCK_RRN7TH)
                .provider(MOCK_PROVIDER)
                .build()

        when:
        def result = sut.verifyRequest(mock)

        then:
        result == ["birthday"]

        where:
        BIRTHDAY     | ignored
        "950302"     | _
        "1995-03-02" | _
        "천구백오십오년"    | _
    }

    def 'verifyRequest - mobile'() {
        given:
        def mock = VerifyRequest.builder()
                .name(MOCK_NAME)
                .mobile(MOBILE)
                .birthday(MOCK_BIRTHDAY)
                .rrn7th(MOCK_RRN7TH)
                .provider(MOCK_PROVIDER)
                .build()

        when:
        def result = sut.verifyRequest(mock)

        then:
        result == ["mobile"]

        where:
        MOBILE             | ignored
        "010-3092-1704"    | _
        "1030921704"       | _
        "+82-10-3092-1704" | _
        "이정훈"              | _
    }

    def 'verifyRequest - provider'() {
        given:
        def mock = VerifyRequest.builder()
                .name(MOCK_NAME)
                .mobile(MOCK_MOBILE)
                .birthday(MOCK_BIRTHDAY)
                .rrn7th(MOCK_RRN7TH)
                .provider(PROVIDER)
                .build()

        when:
        def result = sut.verifyRequest(mock)

        then:
        result == ["provider"]

        where:
        PROVIDER | ignored
        "KTM"    | _
        "KTMOB"  | _
    }

}

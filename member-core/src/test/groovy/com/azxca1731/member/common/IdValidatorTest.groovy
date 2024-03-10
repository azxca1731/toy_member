package com.azxca1731.member.common

import spock.lang.Specification
import spock.lang.Subject

class IdValidatorTest extends Specification {
    @Subject
    IdValidator sut

    def setup() {
        sut = new IdValidator()
    }

    def 'isValidated - works well'() {
        given:
        when:
        def result = sut.isValidated(MOCK_ID)

        then:
        result == RESULT

        where:
        MOCK_ID | RESULT
        "1" | true
        "-1" | false
        "0" | false
        "가" | false
    }
}

package com.azxca1731.member.domain.business

import com.azxca1731.member.common.IdValidator
import spock.lang.Specification
import spock.lang.Subject

class BusinessServiceTest extends Specification {
    @Subject
    BusinessService sut
    def repository = Mock(BusinessRepository)
    def idValidator = Mock(IdValidator)

    def MOCK_MEMBER_ID = 1L
    def MOCK_BUSINESS_ID = 10L
    def MOCK_BUSINESS_NUMBER = "1239381232"
    def MOCK_BUSINESS_NAME = "이가네 통닭"
    def MOCK_ZIP_CODE = "08173"
    def MOCK_ROAD_ADDRESS = "경기도 수원시 영통구 매영로 10"
    def MOCK_ROAD_ADDRESS_DETAIL = "6동 903호"

    def setup() {
        sut = new BusinessService(repository, idValidator)
    }

    def 'createBusiness - works well'() {
        given:
        def request = BusinessCreateRequest.builder()
                .ownerMemberId(MOCK_MEMBER_ID)
                .businessNumber(MOCK_BUSINESS_NUMBER)
                .businessName(MOCK_BUSINESS_NAME)
                .zipCode(MOCK_ZIP_CODE)
                .roadAddress(MOCK_ROAD_ADDRESS)
                .roadAddressDetail(MOCK_ROAD_ADDRESS_DETAIL)
                .build()
        def mockBusiness = Mock(Business)
        mockBusiness.businessId >> MOCK_BUSINESS_ID
        mockBusiness.getOwnerMemberId() >> MOCK_MEMBER_ID
        mockBusiness.getBusinessNumber() >> MOCK_BUSINESS_NUMBER
        mockBusiness.getBusinessName() >> MOCK_BUSINESS_NAME
        mockBusiness.getZipCode() >> MOCK_ZIP_CODE
        mockBusiness.getRoadAddress() >> MOCK_ROAD_ADDRESS
        mockBusiness.getRoadAddressDetail() >> MOCK_ROAD_ADDRESS_DETAIL
        mockBusiness.isMain >> true

        1 * repository.existsByOwnerMemberId(MOCK_MEMBER_ID) >> false
        1 * repository.save({ it -> it.isMain == true }) >> mockBusiness

        when:
        def result = sut.createBusiness(request)

        then:
        result
        result.getOwnerMemberId() == MOCK_MEMBER_ID
        result.isMain
    }

    def 'createBusiness - isMain false'() {
        given:
        def request = BusinessCreateRequest.builder()
                .ownerMemberId(MOCK_MEMBER_ID)
                .businessNumber(MOCK_BUSINESS_NUMBER)
                .businessName(MOCK_BUSINESS_NAME)
                .zipCode(MOCK_ZIP_CODE)
                .roadAddress(MOCK_ROAD_ADDRESS)
                .roadAddressDetail(MOCK_ROAD_ADDRESS_DETAIL)
                .build()
        def mockBusiness = Mock(Business)
        mockBusiness.businessId >> MOCK_BUSINESS_ID
        mockBusiness.getOwnerMemberId() >> MOCK_MEMBER_ID
        mockBusiness.getBusinessNumber() >> MOCK_BUSINESS_NUMBER
        mockBusiness.getBusinessName() >> MOCK_BUSINESS_NAME
        mockBusiness.getZipCode() >> MOCK_ZIP_CODE
        mockBusiness.getRoadAddress() >> MOCK_ROAD_ADDRESS
        mockBusiness.getRoadAddressDetail() >> MOCK_ROAD_ADDRESS_DETAIL
        mockBusiness.isMain >> false

        1 * repository.existsByOwnerMemberId(MOCK_MEMBER_ID) >> true
        1 * repository.save({ it -> it.isMain == false }) >> mockBusiness

        when:
        def result = sut.createBusiness(request)

        then:
        result
        result.getOwnerMemberId() == MOCK_MEMBER_ID
        !result.isMain
    }

    def 'createBusiness - save exception'() {
        given:
        def request = BusinessCreateRequest.builder()
                .ownerMemberId(MOCK_MEMBER_ID)
                .businessNumber(MOCK_BUSINESS_NUMBER)
                .businessName(MOCK_BUSINESS_NAME)
                .zipCode(MOCK_ZIP_CODE)
                .roadAddress(MOCK_ROAD_ADDRESS)
                .roadAddressDetail(MOCK_ROAD_ADDRESS_DETAIL)
                .build()

        1 * repository.existsByOwnerMemberId(MOCK_MEMBER_ID) >> true
        1 * repository.save(*_) >> { throw new RuntimeException() }

        when:
        def result = sut.createBusiness(request)

        then:
        result
        result.getBusinessId() == -1
    }

    def 'findByBusinessId - works well'() {
        given:
        def mockBusiness = Mock(Business)
        mockBusiness.businessId >> MOCK_BUSINESS_ID
        mockBusiness.getOwnerMemberId() >> MOCK_MEMBER_ID
        mockBusiness.getBusinessNumber() >> MOCK_BUSINESS_NUMBER
        mockBusiness.getBusinessName() >> MOCK_BUSINESS_NAME
        mockBusiness.getZipCode() >> MOCK_ZIP_CODE
        mockBusiness.getRoadAddress() >> MOCK_ROAD_ADDRESS
        mockBusiness.getRoadAddressDetail() >> MOCK_ROAD_ADDRESS_DETAIL
        mockBusiness.isMain >> false

        1 * idValidator.isValidated(MOCK_BUSINESS_ID + "") >> true
        1 * repository.findById(MOCK_BUSINESS_ID) >> Optional.of(mockBusiness)

        when:
        def result = sut.findByBusinessId(MOCK_BUSINESS_ID + "")

        then:
        result.businessId == MOCK_BUSINESS_ID
    }

    def 'findByBusinessId - no business'() {
        given:
        1 * idValidator.isValidated(MOCK_BUSINESS_ID + "") >> true
        1 * repository.findById(MOCK_BUSINESS_ID) >> Optional.ofNullable(null)

        when:
        def result = sut.findByBusinessId(MOCK_BUSINESS_ID + "")

        then:
        result.businessId == -1
    }

    def 'findByBusinessId - validator return false'() {
        given:
        1 * idValidator.isValidated(MOCK_BUSINESS_ID + "") >> false
        0 * repository.findById(*_)

        when:
        def result = sut.findByBusinessId(MOCK_BUSINESS_ID + "")

        then:
        result.businessId == -1
    }
}

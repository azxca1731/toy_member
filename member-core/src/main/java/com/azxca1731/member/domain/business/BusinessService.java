package com.azxca1731.member.domain.business;

import com.azxca1731.member.common.IdValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessService {
    private final BusinessRepository repository;
    private final IdValidator idValidator;

    public BusinessResponse createBusiness(BusinessCreateRequest request) {
        boolean isMain = getIsMain(request);
        Business newBusiness = makeNewBusiness(request, isMain);

        try {
            return Optional.of(repository.save(newBusiness))
                    .map(BusinessResponse::of)
                    .orElse(BusinessResponse.empty());
        } catch (RuntimeException e) {
            log.error("Exception occur. please check", e);
        }

        return BusinessResponse.empty();
    }

    private boolean getIsMain(BusinessCreateRequest request) {
        return !repository.existsByOwnerMemberId(request.getOwnerMemberId());
    }

    private Business makeNewBusiness(BusinessCreateRequest request, boolean isMain) {
        return Business.builder()
                .ownerMemberId(request.getOwnerMemberId())
                .businessNumber(request.getBusinessNumber())
                .businessName(request.getBusinessName())
                .zipCode(request.getZipCode())
                .roadAddress(request.getRoadAddress())
                .roadAddressDetail(request.getRoadAddressDetail())
                .isMain(isMain)
                .build();
    }

    public BusinessResponse findByBusinessId(String businessId) {
        if (!idValidator.isValidated(businessId)) {
            return BusinessResponse.empty();
        }

        return repository.findById(Long.parseLong(businessId))
                .map(BusinessResponse::of)
                .orElse(BusinessResponse.empty());
    }
}

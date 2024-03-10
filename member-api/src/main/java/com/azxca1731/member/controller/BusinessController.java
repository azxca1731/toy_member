package com.azxca1731.member.controller;

import com.azxca1731.member.common.ApiUrl;
import com.azxca1731.member.domain.business.BusinessResponse;
import com.azxca1731.member.domain.business.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @GetMapping(ApiUrl.BUSINESS_DETAIL)
    public BusinessResponse getBusiness(@PathVariable String businessId) {
        return businessService.findByBusinessId(businessId);
    }
}

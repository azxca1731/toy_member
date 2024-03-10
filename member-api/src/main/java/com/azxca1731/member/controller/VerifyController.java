package com.azxca1731.member.controller;

import com.azxca1731.member.common.ApiUrl;
import com.azxca1731.member.common.BaseResponse;
import com.azxca1731.member.domain.verify.VerifyRequest;
import com.azxca1731.member.domain.verify.VerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VerifyController {
    private final VerifyService verifyService;

    @PostMapping(ApiUrl.VERIFY)
    public BaseResponse verify(@RequestBody VerifyRequest request) {
        return verifyService.verify(request);
    }
}

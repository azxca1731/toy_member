package com.azxca1731.member.controller;

import com.azxca1731.member.common.ApiUrl;
import com.azxca1731.member.common.BaseResponse;
import com.azxca1731.member.common.exception.NotSavedException;
import com.azxca1731.member.domain.sign.SignService;
import com.azxca1731.member.domain.sign.request.SingOutRequest;
import com.azxca1731.member.domain.sign.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

    private final static String COMMON_FAIL = "시스템이 불안정합니다. 나중에 다시 시도해주세요";

    @PostMapping(ApiUrl.SIGN_UP)
    public BaseResponse signUp(@RequestBody SignUpRequest request) {
        try {
            return signService.signUp(request);
        } catch (NotSavedException ne) {
            log.error("some entity doesn't saved", ne);
            return BaseResponse.failed(COMMON_FAIL);
        }
    }

    @PostMapping(ApiUrl.SIGN_OUT)
    public BaseResponse signOut(@RequestBody SingOutRequest request) {
        return signService.signOut(request);
    }
}

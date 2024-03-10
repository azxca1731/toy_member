package com.azxca1731.member;

import com.azxca1731.member.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private final static String COMMON_FAIL = "시스템이 불안정합니다. 나중에 다시 시도해주세요";

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public BaseResponse handleException(RuntimeException e) {
        log.error("exception occur", e);
        return BaseResponse.failed(COMMON_FAIL);
    }
}

package com.azxca1731.member.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BaseResponse {
    private BaseResponseResult result;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    private Code code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public static BaseResponse failed(Object data, Code code, String message) {
        return BaseResponse.builder()
                .result(BaseResponseResult.FAILED)
                .data(data)
                .code(code)
                .message(message)
                .build();
    }

    public static BaseResponse failed(Code code, String message) {
        return failed(null, code, message);
    }

    public static BaseResponse failed(String message) {
        return failed(Code.COMMON_FAIL, message);
    }

    public static BaseResponse success(Object data, Code code) {
        return BaseResponse.builder()
                .result(BaseResponseResult.SUCCESS)
                .data(data)
                .code(code)
                .build();
    }

    public static BaseResponse success(Code code) {
        return success(null, code);
    }
}

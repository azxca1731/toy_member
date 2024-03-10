package com.azxca1731.member.domain.sign;

import com.azxca1731.member.common.BaseValidator;
import com.azxca1731.member.domain.sign.request.SingUpRequest;
import com.azxca1731.member.security.Md5HashEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class SignUpValidator {
    private final Md5HashEncoder md5HashEncoder;
    private final static Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private static final BaseValidator<SingUpRequest> VALIDATOR = new BaseValidator<>();

    public List<String> verifyRequest(SingUpRequest request) {
        List<String> unValidFields = new ArrayList<>();

        Optional.ofNullable(validateMemberId(request)).ifPresent(unValidFields::add);
        Optional.ofNullable(validateEmail(request)).ifPresent(unValidFields::add);
        Optional.ofNullable(validateRawPassword(request)).ifPresent(unValidFields::add);
        Optional.ofNullable(validateBusinessNumber(request)).ifPresent(unValidFields::add);
        Optional.ofNullable(validateBusinessName(request)).ifPresent(unValidFields::add);
        Optional.ofNullable(validateZipCode(request)).ifPresent(unValidFields::add);
        Optional.ofNullable(validateKey(request)).ifPresent(unValidFields::add);

        return unValidFields;
    }

    private String validateKey(SingUpRequest request) {
        return VALIDATOR.validateField("key",
                it -> md5HashEncoder.matches(request.getMemberId(), request.getKey()),
                SingUpRequest::getKey,
                request
        );
    }

    @Nullable
    private String validateMemberId(SingUpRequest request) {
        // 숫자로 표현되며 0 이상
        return VALIDATOR.validateField("memberId",
                it -> Long.parseLong(it) > 0,
                SingUpRequest::getMemberId,
                request
        );
    }

    @Nullable
    private String validateEmail(SingUpRequest request) {
        // 이메일 형식
        return VALIDATOR.validateField("email",
                it -> EMAIL_PATTERN.matcher(it).matches(),
                SingUpRequest::getEmail,
                request
        );
    }

    @Nullable
    private String validateRawPassword(SingUpRequest request) {
        // 비밀번호 6자이상 30자 미만
        return VALIDATOR.validateField("rawPassword",
                it -> it.length() >= 6 && it.length() < 30,
                SingUpRequest::getRawPassword,
                request
        );
    }

    @Nullable
    private String validateBusinessNumber(SingUpRequest request) {
        // 10 자의 숫자 0 이상
        return VALIDATOR.validateField("businessNumber",
                it -> it.length() == 10 && Long.parseLong(it) > 0,
                SingUpRequest::getBusinessNumber,
                request
        );
    }

    @Nullable
    private String validateBusinessName(SingUpRequest request) {
        // 1글자 이상
        return VALIDATOR.validateField("businessName",
                it -> it.length() > 0,
                SingUpRequest::getBusinessName,
                request
        );
    }

    @Nullable
    private String validateZipCode(SingUpRequest request) {
        // 5자의 숫자 0 이상
        return VALIDATOR.validateField("zipCode",
                it -> it.length() == 5 && Long.parseLong(it) > 0,
                SingUpRequest::getZipCode,
                request
        );
    }

}

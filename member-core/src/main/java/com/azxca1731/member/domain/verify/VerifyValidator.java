package com.azxca1731.member.domain.verify;

import com.azxca1731.member.common.BaseValidator;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class VerifyValidator {
    private static final BaseValidator<VerifyRequest> VALIDATOR = new BaseValidator<>();
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$");
    private static final Pattern BIRTHDAY_PATTERN = Pattern.compile("^\\d{4}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$");


    public List<String> verifyRequest(VerifyRequest request) {
        List<String> unValidFields = new ArrayList<>();

        Optional.ofNullable(validateName(request)).ifPresent(unValidFields::add);
        Optional.ofNullable(validateProvider(request)).ifPresent(unValidFields::add);
        Optional.ofNullable(validateMobile(request)).ifPresent(unValidFields::add);
        Optional.ofNullable(validateBirthDay(request)).ifPresent(unValidFields::add);
        Optional.ofNullable(validateRrn7th(request)).ifPresent(unValidFields::add);

        return unValidFields;
    }


    @Nullable
    private String validateName(VerifyRequest request) {
        return VALIDATOR.validateField("name",
                it -> it.length() >= 2 && it.length() < 20,
                VerifyRequest::getName,
                request
        );
    }

    @Nullable
    private String validateRrn7th(VerifyRequest request) {
        return VALIDATOR.validateField("rrn7th",
                it -> it.length() == 1 && Character.isDigit(it.charAt(0)),
                VerifyRequest::getRrn7th,
                request
        );
    }

    @Nullable
    private String validateBirthDay(VerifyRequest request) {
        return VALIDATOR.validateField("birthday",
                it -> BIRTHDAY_PATTERN.matcher(it).matches(),
                VerifyRequest::getBirthday,
                request
        );
    }

    @Nullable
    private String validateMobile(VerifyRequest request) {
        return VALIDATOR.validateField("mobile",
                it -> PHONE_NUMBER_PATTERN.matcher(it).matches(),
                VerifyRequest::getMobile,
                request
        );
    }

    @Nullable
    private String validateProvider(VerifyRequest request) {
        return VALIDATOR.validateField("provider",
                it -> it.length() >= 6,
                VerifyRequest::getProvider,
                request
        );
    }
}

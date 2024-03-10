package com.azxca1731.member.common;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class BaseValidator<T> {
    public String validateField(String fieldName, Predicate<String> condition, Function<T, String> mapper, T request) {
        Object verified = Optional.ofNullable(request)
                .map(mapper)
                .filter(condition)
                .orElse(null);

        return Objects.isNull(verified) ? fieldName : null;
    }

    public static boolean isDigit(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

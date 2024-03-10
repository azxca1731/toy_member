package com.azxca1731.member.common;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class DebugModeCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String debugMode = context.getEnvironment().getProperty("debug.mode");
        return "true".equalsIgnoreCase(debugMode);
    }
}

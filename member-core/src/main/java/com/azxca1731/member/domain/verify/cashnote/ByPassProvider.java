package com.azxca1731.member.domain.verify.cashnote;

import com.azxca1731.member.common.DebugModeCondition;
import com.azxca1731.member.domain.verify.VerifyProvider;
import com.azxca1731.member.domain.verify.VerifyRequest;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(DebugModeCondition.class)
public class ByPassProvider implements VerifyProvider {
    @Override
    public boolean verify(VerifyRequest verifyRequest) {
        return true;
    }
}

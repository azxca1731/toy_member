package com.azxca1731.member.domain.verify.cashnote;

import com.azxca1731.member.common.NonDebugModeCondition;
import com.azxca1731.member.common.ResilienceRestClientHolder;
import com.azxca1731.member.domain.verify.VerifyRequest;
import com.azxca1731.member.domain.verify.VerifyProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

@Slf4j
@Component
@Conditional(NonDebugModeCondition.class)
@RequiredArgsConstructor
class CashNoteProvider implements VerifyProvider {
    private final static String PROVIDER_URL = "https://auth.cashnote.kr/verify-identity";
    private final ResilienceRestClientHolder restClientHolder;

    @Override
    public boolean verify(VerifyRequest verifyRequest) {
        CashNoteProviderResult result = checkByWeb(verifyRequest);
        return CashNoteProviderResult.SUCCESS.equals(result);
    }

    private CashNoteProviderResult checkByWeb(VerifyRequest verifyRequest) {

        CashNoteProviderResponse res = null;
        try {
            res = restClientHolder.getClient()
                    .post()
                    .uri(PROVIDER_URL)
                    .body(verifyRequest)
                    .retrieve()
                    .body(CashNoteProviderResponse.class);
        } catch (HttpStatusCodeException sce) {
            log.error("CashNote verification service throw error. code: {}, error-text: {}, input: {}.\nerror: {}",
                    sce.getStatusCode(), sce.getStatusText(), verifyRequest, sce);
        } catch (RestClientException rce) {
            log.error("CashNote verification service throw error. input:{}\nerror: {}", verifyRequest, rce);
        }

        return Optional.ofNullable(res)
                .map(CashNoteProviderResponse::getResult)
                .orElse(CashNoteProviderResult.FAILED);
    }
}

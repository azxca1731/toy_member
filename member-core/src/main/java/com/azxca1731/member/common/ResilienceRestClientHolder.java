package com.azxca1731.member.common;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ResilienceRestClientHolder {

    // TODO Resilience 4j 기반으로 알럿, 폴백, fast fail이 가능하게 변경
    public RestClient getClient() {
        return RestClient.create();
    }
}

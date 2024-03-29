package com.azxca1731.member.domain.sign.kafka;

import com.azxca1731.member.common.ApiUrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignOutTopicMessage {
    @Builder.Default
    private final Map<String, Map<String, String>> apiInfo = Map.of(
            "memberId", Map.of("api", ApiUrl.MEMBER_DETAIL, "method", HttpMethod.GET.name())
    );
    private long memberId;
}

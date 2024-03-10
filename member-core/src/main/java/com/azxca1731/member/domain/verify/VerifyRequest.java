package com.azxca1731.member.domain.verify;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyRequest {
    private String name;
    private String mobile;
    private String birthday;
    private String rrn7th;
    private String provider;
}
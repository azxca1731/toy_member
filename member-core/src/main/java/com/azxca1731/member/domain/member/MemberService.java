package com.azxca1731.member.domain.member;

import com.azxca1731.member.common.IdValidator;
import com.azxca1731.member.domain.member.request.ChangeRegularMemberRequest;
import com.azxca1731.member.domain.member.request.ChangeRegularMemberToDBRequest;
import com.azxca1731.member.domain.member.request.EnrollMemberCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final IdValidator idValidator;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponse createEnrollMember(EnrollMemberCreateRequest enrollMemberCreateRequest) {
        Member newMember = makeNewMember(enrollMemberCreateRequest);
        try {
            return Optional.of(memberRepository.save(newMember))
                    .map(MemberResponse::of)
                    .orElse(MemberResponse.empty());
        } catch (RuntimeException e) {
            log.error("Exception occur. please check", e);
        }

        return MemberResponse.empty();
    }

    private Member makeNewMember(EnrollMemberCreateRequest enrollMemberCreateRequest) {
        return Member.builder()
                .birthday(enrollMemberCreateRequest.getBirthday())
                .mobile(enrollMemberCreateRequest.getMobile())
                .name(enrollMemberCreateRequest.getName())
                .rrn7th(enrollMemberCreateRequest.getRrn7th())
                .provider(enrollMemberCreateRequest.getProvider())
                .memberStatus(MemberStatus.ENROLLED)
                .build();
    }

    public MemberResponse changeRegularFromEnroll(ChangeRegularMemberRequest request) {
        boolean isExist = memberRepository.existsById(request.getMemberId());
        if (!isExist) {
            log.warn("member doesn't exist. please check the param. input: {}", request);
            return MemberResponse.empty();
        }

        String password = encrypt(request.getRawPassword());

        ChangeRegularMemberToDBRequest dbRequest = makeDBRequest(request, password);

        long count = memberRepository.changeToRegularMember(dbRequest);
        if (count == 0) {
            log.error("there's no db changed input: {}", dbRequest);
            return MemberResponse.empty();
        }

        return memberRepository.findById(request.getMemberId())
                .map(MemberResponse::of)
                .orElseGet(() -> {
                    log.error("member doesn't changed. input: {}", dbRequest);
                    return MemberResponse.empty();
                });
    }

    private ChangeRegularMemberToDBRequest makeDBRequest(ChangeRegularMemberRequest request, String password) {
        return ChangeRegularMemberToDBRequest.builder()
                .memberId(request.getMemberId())
                .email(request.getEmail())
                .password(password)
                .build();
    }

    private String encrypt(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public MemberResponse findByMemberId(String memberId) {
        if (idValidator.isValidated(memberId)) {
            return MemberResponse.empty();
        }

        return memberRepository.findById(Long.parseLong(memberId))
                .map(MemberResponse::of)
                .orElse(MemberResponse.empty());
    }

    public long changeToWithdrawn(String memberId) {
        if (!idValidator.isValidated(memberId)) {
            return 0;
        }
        return memberRepository.changeToWithdrawn(memberId);
    }
}

package com.azxca1731.member.domain.pii;

import com.azxca1731.member.common.IdValidator;
import com.azxca1731.member.domain.business.Business;
import com.azxca1731.member.domain.business.BusinessRepository;
import com.azxca1731.member.domain.member.Member;
import com.azxca1731.member.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetentionService {
    private final IdValidator idValidator;
    private final MemberRepository memberRepository;
    private final BusinessRepository businessRepository;
    private final WithdrawMemberRepository withdrawMemberRepository;
    private final WithdrawBusinessRepository withdrawBusinessRepository;

    @Transactional
    public long migrateUserInfo(String memberId) {
        if (!idValidator.isValidated(memberId)) {
            log.info("Member id is weird. input: {}", memberId);
            return 0;
        }

        long count = 0;

        Member member = memberRepository.findById(Long.parseLong(memberId)).orElse(null);
        if (Objects.isNull(member)) {
            log.info("there's no member info. input: {}", memberId);
            return 0;
        }

        List<Business> businessList = businessRepository.findAllByOwnerMemberId(Long.parseLong(memberId));

        withdrawMemberRepository.save(makeWithdrawMember(member));
        count++;

        List<WithdrawBusiness> withdrawBusinessList = withdrawBusinessRepository.saveAll(makeWithdrawBusinessList(businessList));
        count += withdrawBusinessList.size();

        memberRepository.delete(member);
        businessRepository.deleteAll(businessList);

        log.info("member id {} migrated. count: {}", memberId, count);

        return count;
    }

    private List<WithdrawBusiness> makeWithdrawBusinessList(List<Business> businessList) {
        return businessList.stream().map(this::makeWithdrawBusiness).toList();
    }

    private WithdrawBusiness makeWithdrawBusiness(Business business) {
        return WithdrawBusiness.builder()
                .businessId(business.getBusinessId())
                .ownerMemberId(business.getOwnerMemberId())
                .businessNumber(business.getBusinessNumber())
                .businessName(business.getBusinessName())
                .zipCode(business.getZipCode())
                .roadAddress(business.getRoadAddress())
                .roadAddressDetail(business.getRoadAddressDetail())
                .isMain(business.getIsMain())
                .createdAt(business.getCreatedAt())
                .updatedAt(business.getUpdatedAt())
                .build();
    }

    private WithdrawMember makeWithdrawMember(Member member) {
        return WithdrawMember.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .mobile(member.getMobile())
                .birthday(member.getBirthday())
                .rrn7th(member.getRrn7th())
                .provider(member.getProvider())
                .memberStatus(member.getMemberStatus())
                .withdrawAt(member.getWithdrawAt())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}

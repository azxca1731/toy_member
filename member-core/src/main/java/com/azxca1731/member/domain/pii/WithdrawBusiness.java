package com.azxca1731.member.domain.pii;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "withdraw_business", schema = "member")
public class WithdrawBusiness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessId;

    @Column(nullable = false)
    private Long ownerMemberId;

    @Column(nullable = false, length = 50)
    private String businessNumber;

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false, length = 50)
    private String zipCode;

    @Column(nullable = false)
    private String roadAddress;

    private String roadAddressDetail;

    private Boolean isMain;

    @Builder.Default
    private LocalDateTime migratedAt = LocalDateTime.now();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
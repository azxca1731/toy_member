package com.azxca1731.member.domain.business;

import com.azxca1731.member.common.BaseEntity;
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

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "business", schema = "member")
public class Business extends BaseEntity {
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
}
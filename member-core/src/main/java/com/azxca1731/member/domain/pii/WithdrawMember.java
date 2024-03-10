package com.azxca1731.member.domain.pii;

import com.azxca1731.member.domain.member.MemberStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "withdraw_member", schema = "member")
public class WithdrawMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String email;

    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String mobile;

    @Column(nullable = false, length = 50)
    private String birthday;

    @Column(nullable = false, length = 10)
    private String rrn7th;

    @Column(nullable = false, length = 50)
    private String provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MemberStatus memberStatus;

    private LocalDateTime withdrawAt;

    @Builder.Default
    private LocalDateTime migratedAt = LocalDateTime.now();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

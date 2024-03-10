package com.azxca1731.member.domain.business;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    boolean existsByOwnerMemberId(Long ownerMemberId);
}

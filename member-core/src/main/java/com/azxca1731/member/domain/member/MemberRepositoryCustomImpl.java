package com.azxca1731.member.domain.member;

import com.azxca1731.member.domain.member.request.ChangeRegularMemberToDBRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QMember qMember = QMember.member;

    public MemberRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public long changeToRegularMember(ChangeRegularMemberToDBRequest request) {
        return queryFactory.update(qMember)
                .where(qMember.memberId.eq(request.getMemberId()).and(qMember.memberStatus.eq(MemberStatus.ENROLLED)))
                .set(qMember.password, request.getPassword())
                .set(qMember.email, request.getEmail())
                .set(qMember.memberStatus, MemberStatus.REGULAR)
                .set(qMember.updatedAt, LocalDateTime.now())
                .execute();
    }

    @Override
    @Transactional
    public long changeToWithdrawn(String memberId) {
        return queryFactory.update(qMember)
                .where(qMember.memberId.eq(Long.valueOf(memberId)).and(qMember.memberStatus.eq(MemberStatus.REGULAR)))
                .set(qMember.memberStatus, MemberStatus.WITHDRAW)
                .set(qMember.withdrawAt, LocalDateTime.now())
                .set(qMember.updatedAt, LocalDateTime.now())
                .execute();
    }
}

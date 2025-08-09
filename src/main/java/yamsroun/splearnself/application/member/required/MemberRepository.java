package yamsroun.splearnself.application.member.required;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.util.Optional;

import yamsroun.splearnself.domain.member.Member;
import yamsroun.splearnself.domain.member.Profile;
import yamsroun.splearnself.domain.shared.Email;

/**
 * 회원 정보를 저장하거나 조회한다
 */
public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);

    @Query("select m from Member m where m.detail.profile = :profile")
    Optional<Member> findByProfile(Profile profile);
}

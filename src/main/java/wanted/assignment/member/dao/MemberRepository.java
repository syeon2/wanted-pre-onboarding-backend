package wanted.assignment.member.dao;

import java.util.Optional;

import wanted.assignment.member.dao.domain.Member;

public interface MemberRepository {

	Long save(Member member);

	Member findById(Long id);

	Optional<Member> findByEmail(String email);

	Long update(Long id, Member member);

	void delete(Long id);
}

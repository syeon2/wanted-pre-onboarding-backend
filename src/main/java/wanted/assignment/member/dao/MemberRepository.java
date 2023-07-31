package wanted.assignment.member.dao;

import wanted.assignment.member.dao.domain.Member;

public interface MemberRepository {

	Long save(Member member);

	Member findById(Long id);

	Long update(Long id, Member member);

	void delete(Long id);
}

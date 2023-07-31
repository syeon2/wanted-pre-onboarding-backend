package wanted.assignment.member.dao.mybatis;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import wanted.assignment.common.basewrapper.BaseEntity;
import wanted.assignment.member.dao.MemberRepository;
import wanted.assignment.member.dao.domain.Member;

@Repository
@RequiredArgsConstructor
public class MyBatisMemberRepository implements MemberRepository {

	private final MemberMapper memberMapper;

	@Override
	public Long save(Member member) {
		BaseEntity<Member> createData = BaseEntity.createData(member);
		memberMapper.save(createData);

		return createData.getData().getId();
	}

	@Override
	public Member findById(Long id) {
		return memberMapper.findById(id)
			.orElseThrow(() -> new NoSuchElementException("해당 Id의 회원은 존재하지 않습니다."));
	}

	@Override
	public Long update(Long id, Member member) {
		BaseEntity<Member> updateData = BaseEntity.updateData(member);
		memberMapper.update(id, updateData);

		return id;
	}

	@Override
	public void delete(Long id) {
		memberMapper.delete(id);
	}
}

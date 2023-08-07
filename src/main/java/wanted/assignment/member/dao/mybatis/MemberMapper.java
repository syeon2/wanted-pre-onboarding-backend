package wanted.assignment.member.dao.mybatis;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import wanted.assignment.common.basewrapper.BaseEntity;
import wanted.assignment.member.dao.domain.Member;

@Mapper
public interface MemberMapper {

	void save(BaseEntity<Member> member);

	Optional<Member> findById(Long id);

	Optional<Member> findByEmail(String email);

	void update(@Param("id") Long id, @Param("updateParam") BaseEntity<Member> member);

	void delete(Long id);
}

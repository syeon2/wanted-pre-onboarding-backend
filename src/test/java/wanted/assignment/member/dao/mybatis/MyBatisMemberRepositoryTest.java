package wanted.assignment.member.dao.mybatis;

import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import wanted.assignment.TestBaseConfig;
import wanted.assignment.member.dao.MemberRepository;
import wanted.assignment.member.dao.domain.Member;

@SpringBootTest
class MyBatisMemberRepositoryTest extends TestBaseConfig {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@DisplayName("회원을 저장합니다.")
	void save() {
		// given
		Member member = createMember("xxxx@gmail.com", "xxxxxxxx");

		// when
		Long savedMemberId = memberRepository.save(member);

		// then
		assertThat(savedMemberId).isEqualTo(1L);
	}

	@Test
	@DisplayName("아이디를 사용하여 회원을 찾습니다.")
	void findById() {
		// given
		String email = "xxxx@gmail.com";
		String password = "xxxxxxxx";

		Member member = createMember(email, password);
		memberRepository.save(member);

		// when
		Member findMember = memberRepository.findById(1L);

		// then
		assertThat(findMember)
			.extracting("id", "email", "password")
			.contains(1L, email, password);
	}

	@Test
	@DisplayName("아이디를 사용하여 회원 정보를 수정합니다.")
	void update() {
		// given
		String email = "xxxx@gmail.com";
		String password = "xxxxxxxx";

		Member memberA = createMember(email, password);
		Long savedMemberId = memberRepository.save(memberA);

		// when
		String updateEmail = "yyyyy@gmail.com";
		String updatePassword = "yyyyyyy";

		Member updateMember = createMember(updateEmail, updatePassword);
		Long updateMemberId = memberRepository.update(savedMemberId, updateMember);

		// then
		assertThat(updateMemberId).isEqualTo(savedMemberId);

		Member findMember = memberRepository.findById(updateMemberId);
		assertThat(findMember)
			.extracting("id", "email", "password")
			.contains(updateMemberId, updateEmail, updatePassword);
	}

	@Test
	@DisplayName("아이디를 사용하여 회원을 삭제합니다.")
	void delete() {
		// given
		String email = "xxxx@gmail.com";
		String password = "xxxxxxxx";

		Member memberA = createMember(email, password);
		Long savedMemberId = memberRepository.save(memberA);

		Member findMember = memberRepository.findById(savedMemberId);
		assertThat(findMember)
			.extracting("id", "email", "password")
			.contains(savedMemberId, email, password);

		// when
		memberRepository.delete(savedMemberId);

		// then
		assertThatThrownBy(() -> memberRepository.findById(savedMemberId))
			.isInstanceOf(NoSuchElementException.class);
	}

	private Member createMember(String email, String password) {
		return Member.builder()
			.email(email)
			.password(password)
			.build();
	}
}

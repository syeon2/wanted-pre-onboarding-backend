package wanted.assignment.member.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import wanted.assignment.TestBaseConfig;
import wanted.assignment.member.dao.MemberRepository;
import wanted.assignment.member.dao.domain.Member;
import wanted.assignment.member.service.request.MemberCreateServiceRequest;

class MemberServiceTest extends TestBaseConfig {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@DisplayName("Request DTO를 받아 비밀번호 암호화 후 DB에 저장합니다.")
	void join() {
		// given
		String email = "xxxxx@gmail.com";
		String password = "xxxxxxx";

		MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
			.email(email)
			.password(password)
			.build();

		// when
		Long joinedMemberId = memberService.join(request);

		// then
		assertThat(joinedMemberId).isEqualTo(1L);

		Member findMember = memberRepository.findById(joinedMemberId);
		assertThat(findMember.getPassword()).isNotEqualTo(password);
	}
}

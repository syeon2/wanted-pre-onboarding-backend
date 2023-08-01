package wanted.assignment.member.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import wanted.assignment.TestBaseConfig;
import wanted.assignment.common.error.exception.member.DuplicateEmailException;
import wanted.assignment.member.dao.MemberRepository;
import wanted.assignment.member.dao.domain.Member;
import wanted.assignment.member.service.request.MemberCreateServiceRequest;

class MemberServiceTest extends TestBaseConfig {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	private final String email = "xxxxx@gmail.com";
	private final String password = "xxxxxxx";

	@Test
	@DisplayName("Request DTO를 받아 비밀번호 암호화 후 DB에 저장합니다.")
	void join() {
		// given
		MemberCreateServiceRequest request = getMemberCreateServiceRequest(email, password);

		// when
		Long joinedMemberId = memberService.join(request);

		// then
		assertThat(joinedMemberId).isEqualTo(1L);

		Member findMember = memberRepository.findById(joinedMemberId);
		assertThat(findMember.getPassword()).isNotEqualTo(password);
	}

	@Test
	@DisplayName("이미 사용 중인 이메일이라면 예외를 던집니다.")
	void isDuplicateEmail() {
		// given
		MemberCreateServiceRequest request = getMemberCreateServiceRequest(email, password);
		memberService.join(request);

		// when  // then
		assertThatThrownBy(() -> memberService.join(request))
			.isInstanceOf(DuplicateEmailException.class);
	}

	private MemberCreateServiceRequest getMemberCreateServiceRequest(String email, String password) {
		return MemberCreateServiceRequest.builder()
			.email(email)
			.password(password)
			.build();
	}
}

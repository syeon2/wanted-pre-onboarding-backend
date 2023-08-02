package wanted.assignment.member.dao.domain;

import lombok.Builder;
import lombok.Getter;
import wanted.assignment.common.generator.Sha256Util;
import wanted.assignment.member.service.request.MemberCreateServiceRequest;
import wanted.assignment.member.web.response.MemberSignInResponse;

@Getter
public class Member {

	private Long id;
	private final String email;
	private final String password;

	@Builder
	private Member(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public static Member createFromServiceRequest(MemberCreateServiceRequest request) {
		String encryptPassword = Sha256Util.getEncrypt(request.getPassword());

		return Member.builder()
			.email(request.getEmail())
			.password(encryptPassword)
			.build();
	}

	public MemberSignInResponse getSignInResponse(String jwtAuthToken) {
		return MemberSignInResponse.builder()
			.id(this.id)
			.email(this.email)
			.token(jwtAuthToken)
			.build();
	}
}

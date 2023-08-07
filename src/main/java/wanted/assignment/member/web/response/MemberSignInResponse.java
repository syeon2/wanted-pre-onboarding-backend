package wanted.assignment.member.web.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberSignInResponse {
	private final Long id;
	private final String email;
	private final String token;

	@Builder
	private MemberSignInResponse(Long id, String email, String token) {
		this.id = id;
		this.email = email;
		this.token = token;
	}
}

package wanted.assignment.member.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateServiceRequest {

	private final String email;
	private final String password;

	@Builder
	private MemberCreateServiceRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}

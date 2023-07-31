package wanted.assignment.member.dao.domain;

import lombok.Builder;
import lombok.Getter;

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
}

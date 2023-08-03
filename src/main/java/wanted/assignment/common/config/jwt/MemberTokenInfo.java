package wanted.assignment.common.config.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberTokenInfo {

	private final Long id;
	private final String email;

	@Builder
	private MemberTokenInfo(Long id, String email) {
		this.id = id;
		this.email = email;
	}
}


package wanted.assignment.member.web.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.Getter;
import wanted.assignment.member.service.request.MemberCreateServiceRequest;

@Getter
public class MemberSignUpRequest {

	@NotBlank(message = "이메일은 필수값입니다.")
	@Email(message = "올바른 이메일 형식으로 입력해주세요.")
	private final String email;

	@NotNull(message = "비밀번호는 필수값입니다.")
	@Length(min = 8, message = "비밀번호는 8자 이상 입력해주세요.")
	private final String password;

	@Builder
	private MemberSignUpRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public MemberCreateServiceRequest toServiceRequest() {
		return MemberCreateServiceRequest.builder()
			.email(this.email)
			.password(this.password)
			.build();
	}
}

package wanted.assignment.post.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import wanted.assignment.post.service.request.PostCreateServiceRequest;

@Getter
public class PostCreateRequest {

	@NotBlank(message = "제목은 빈칸을 허용하지 않습니다.")
	private final String title;

	@NotNull(message = "회원 고유번호는 필수값입니다.")
	private final Long userId;

	@NotBlank(message = "게시글 내용은 빈칸을 허용하지 않습니다.")
	private final String content;

	@Builder
	private PostCreateRequest(String title, Long userId, String content) {
		this.title = title;
		this.userId = userId;
		this.content = content;
	}

	public PostCreateServiceRequest toServiceRequest() {
		return PostCreateServiceRequest.builder()
			.title(this.title)
			.userId(this.userId)
			.content(this.content)
			.build();
	}
}

package wanted.assignment.post.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import wanted.assignment.post.service.request.PostUpdateServiceRequest;

@Getter
public class PostUpdateRequest {

	@NotNull(message = "게시글 아이디는 필수값입니다.")
	private final Long postId;

	@NotBlank(message = "제목은 빈칸을 허용하지 않습니다.")
	private final String title;

	@NotBlank(message = "게시글은 빈칸을 허용하지 않습니다.")
	private final String content;

	@Builder
	private PostUpdateRequest(Long postId, String title, String content) {
		this.postId = postId;
		this.title = title;
		this.content = content;
	}

	public PostUpdateServiceRequest toServiceRequest() {
		return PostUpdateServiceRequest.builder()
			.title(this.title)
			.content(this.content)
			.build();
	}
}

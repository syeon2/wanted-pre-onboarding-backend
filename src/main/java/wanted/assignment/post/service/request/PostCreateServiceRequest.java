package wanted.assignment.post.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreateServiceRequest {

	private final String title;
	private final Long userId;
	private final String content;

	@Builder
	private PostCreateServiceRequest(String title, Long userId, String content) {
		this.title = title;
		this.userId = userId;
		this.content = content;
	}
}

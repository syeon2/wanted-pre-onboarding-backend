package wanted.assignment.post.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdateServiceRequest {

	private final String title;
	private final String content;

	@Builder
	private PostUpdateServiceRequest(String title, String content) {
		this.title = title;
		this.content = content;
	}
}

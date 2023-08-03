package wanted.assignment.post.dao.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDetail {

	private final Long postId;
	private final String content;

	@Builder
	private PostDetail(Long postId, String content) {
		this.postId = postId;
		this.content = content;
	}
}

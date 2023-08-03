package wanted.assignment.post.service.response;

import lombok.Builder;
import lombok.Getter;
import wanted.assignment.post.dao.domain.Post;
import wanted.assignment.post.dao.domain.PostDetail;

@Getter
public class PostResponse {

	private final Long id;
	private final String title;
	private final Integer viewCount;
	private final Long userId;
	private final String content;

	@Builder
	private PostResponse(Long id, String title, Integer viewCount, Long userId, String content) {
		this.id = id;
		this.title = title;
		this.viewCount = viewCount;
		this.userId = userId;
		this.content = content;
	}

	public static PostResponse of(Post post, PostDetail postDetail) {
		return PostResponse.builder()
			.id(post.getId())
			.title(post.getTitle())
			.viewCount(post.getViewCount())
			.userId(post.getUserId())
			.content(postDetail.getContent())
			.build();
	}
}

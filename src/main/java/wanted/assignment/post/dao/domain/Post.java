package wanted.assignment.post.dao.domain;

import lombok.Builder;
import lombok.Getter;
import wanted.assignment.post.service.request.PostCreateServiceRequest;
import wanted.assignment.post.service.request.PostUpdateServiceRequest;

@Getter
public class Post {

	private Long id;
	private final String title;
	private final Integer viewCount;
	private final Long userId;

	@Builder
	private Post(Long id, String title, Integer viewCount, Long userId) {
		this.id = id;
		this.title = title;
		this.viewCount = viewCount;
		this.userId = userId;
	}

	public static Post createFromServiceRequest(PostCreateServiceRequest request) {
		return Post.builder()
			.title(request.getTitle())
			.viewCount(0)
			.userId(request.getUserId())
			.build();
	}

	public static Post updateFromServiceRequest(PostUpdateServiceRequest request) {
		return Post.builder()
			.title(request.getTitle())
			.build();
	}
}

package wanted.assignment.post.dao.domain;

import lombok.Builder;
import lombok.Getter;
import wanted.assignment.post.service.request.PostCreateServiceRequest;
import wanted.assignment.post.service.request.PostUpdateServiceRequest;

@Getter
public class PostDetail {

	private Long id;
	private final String title;
	private final Integer viewCount;
	private final String content;
	private final Long userId;

	@Builder
	private PostDetail(Long id, String title, Integer viewCount, String content, Long userId) {
		this.id = id;
		this.title = title;
		this.viewCount = viewCount;
		this.content = content;
		this.userId = userId;
	}

	public static PostDetail createFromServiceRequest(PostCreateServiceRequest request) {
		return PostDetail.builder()
			.title(request.getTitle())
			.viewCount(0)
			.content(request.getContent())
			.userId(request.getUserId())
			.build();
	}

	public static PostDetail updateFromServiceRequest(PostUpdateServiceRequest request) {
		return PostDetail.builder()
			.title(request.getTitle())
			.content(request.getContent())
			.build();
	}
}

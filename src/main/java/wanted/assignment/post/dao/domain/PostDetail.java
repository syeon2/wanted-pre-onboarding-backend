package wanted.assignment.post.dao.domain;

import lombok.Builder;
import lombok.Getter;
import wanted.assignment.post.service.request.PostCreateServiceRequest;
import wanted.assignment.post.service.request.PostUpdateServiceRequest;

@Getter
public class PostDetail {

	private final Long postId;
	private final String content;

	@Builder
	private PostDetail(Long postId, String content) {
		this.postId = postId;
		this.content = content;
	}

	public static PostDetail createPostDetailFromServiceRequest(PostCreateServiceRequest request, Long postId) {
		return PostDetail.builder()
			.postId(postId)
			.content(request.getContent())
			.build();
	}

	public static PostDetail updatePostDetailFromServiceRequest(PostUpdateServiceRequest request) {
		return PostDetail.builder()
			.content(request.getContent())
			.build();
	}
}

package wanted.assignment.post.dao.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSimple {

	private Long id;
	private final String title;
	private final Integer viewCount;
	private final Long userId;

	@Builder
	private PostSimple(Long id, String title, Integer viewCount, Long userId) {
		this.id = id;
		this.title = title;
		this.viewCount = viewCount;
		this.userId = userId;
	}
}

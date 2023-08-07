package wanted.assignment.comment.dao;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {

	private final String reply;
	private final Long postId;
	private final Long memberId;

	@Builder
	private Comment(Long postId, String reply, Long memberId) {
		this.postId = postId;
		this.reply = reply;
		this.memberId = memberId;
	}
}

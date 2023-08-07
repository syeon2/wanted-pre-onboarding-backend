package wanted.assignment.comment.mybatis;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import wanted.assignment.comment.CommentRepository;

@Repository
@RequiredArgsConstructor
public class MybatisCommentRepository implements CommentRepository {

	private final CommentMapper commentMapper;

	@Override
	public void delete(Long postId) {
		commentMapper.delete(postId);
	}
}

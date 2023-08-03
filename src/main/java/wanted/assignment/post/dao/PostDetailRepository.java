package wanted.assignment.post.dao;

import wanted.assignment.post.dao.domain.PostDetail;

public interface PostDetailRepository {

	Long save(PostDetail postDetail);

	PostDetail findById(Long postId);

	Long update(Long postId, PostDetail postDetail);

	void delete(Long postId);
}

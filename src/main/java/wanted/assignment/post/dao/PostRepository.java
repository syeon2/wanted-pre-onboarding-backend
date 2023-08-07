package wanted.assignment.post.dao;

import java.util.List;

import wanted.assignment.post.dao.domain.PostDetail;
import wanted.assignment.post.dao.domain.PostSimple;

public interface PostRepository {

	Long save(PostDetail post);

	PostDetail findById(Long id);

	List<PostSimple> findAll(Long id);

	Long update(Long id, PostDetail post);

	void delete(Long id);
}

package wanted.assignment.post.dao;

import java.util.List;

import wanted.assignment.post.dao.domain.Post;

public interface PostRepository {

	Long save(Post post);

	Post findById(Long id);

	List<Post> findAll(Long id);

	Long update(Long id, Post post);

	void delete(Long id);
}

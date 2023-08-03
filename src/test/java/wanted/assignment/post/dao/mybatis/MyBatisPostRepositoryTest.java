package wanted.assignment.post.dao.mybatis;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import wanted.assignment.TestBaseConfig;
import wanted.assignment.post.dao.PostRepository;
import wanted.assignment.post.dao.domain.Post;

class MyBatisPostRepositoryTest extends TestBaseConfig {

	@Autowired
	private PostRepository postRepository;

	@Test
	@DisplayName("게시글을 추가합니다.")
	void save() {
		// given
		Post post = createPost("제목");

		// when
		Long savedPostId = postRepository.save(post);

		// then
		assertThat(savedPostId).isEqualTo(1L);
	}

	@Test
	@DisplayName("게시글 아이디를 사용하여 해당 게시글을 찾습니다.")
	void findById() {
		// given
		String title = "title";
		Post post = createPost(title);
		Long savedPostId = postRepository.save(post);

		assertThat(savedPostId).isEqualTo(1L);

		// when
		Post findPost = postRepository.findById(savedPostId);

		// then
		assertThat(findPost)
			.extracting("id", "title", "viewCount", "userId")
			.contains(1L, title, 0, 1L);
	}

	@Test
	@DisplayName("게시글을 모두 조회합니다. (커서 페이지네이션")
	void findAll() {
		// given
		Post post1 = createPost("title1");
		Post post2 = createPost("title2");
		Post post3 = createPost("title3");
		Post post4 = createPost("title4");
		Post post5 = createPost("title5");
		Post post6 = createPost("title6");
		Post post7 = createPost("title7");
		Post post8 = createPost("title8");
		Post post9 = createPost("title9");
		Post post10 = createPost("title10");
		Post post11 = createPost("title11");

		postRepository.save(post1);
		postRepository.save(post2);
		postRepository.save(post3);
		postRepository.save(post4);
		postRepository.save(post5);
		postRepository.save(post6);
		postRepository.save(post7);
		postRepository.save(post8);
		postRepository.save(post9);
		postRepository.save(post10);
		postRepository.save(post11);

		// when
		List<Post> all = postRepository.findAll(2L);

		// then
		assertThat(all).hasSize(10)
			.extracting("id", "title", "viewCount", "userId")
			.containsExactlyInAnyOrder(
				tuple(2L, "title2", 0, 1L),
				tuple(3L, "title3", 0, 1L),
				tuple(4L, "title4", 0, 1L),
				tuple(5L, "title5", 0, 1L),
				tuple(6L, "title6", 0, 1L),
				tuple(7L, "title7", 0, 1L),
				tuple(8L, "title8", 0, 1L),
				tuple(9L, "title9", 0, 1L),
				tuple(10L, "title10", 0, 1L),
				tuple(11L, "title11", 0, 1L)
			);
	}

	@Test
	@DisplayName("게시글 아이디에 해당하는 게시글을 수정합니다.")
	void update() {
		// given
		String title1 = "title1";
		Post post1 = createPost(title1);
		Long savedPostId = postRepository.save(post1);

		assertThat(savedPostId).isEqualTo(1L);

		// when
		String title2 = "title2";
		Post post2 = createPost(title2);

		postRepository.update(savedPostId, post2);

		// then
		Post findPost = postRepository.findById(savedPostId);

		assertThat(findPost)
			.extracting("id", "title", "viewCount", "userId")
			.contains(1L, title2, 0, 1L);
	}

	@Test
	@DisplayName("게시글 아이디를 사용하여 해당 게시글을 삭제합니다.")
	void delete() {
		// given
		Post post = createPost("title1");
		Long savedPostId = postRepository.save(post);

		assertThat(savedPostId).isEqualTo(1L);

		// when
		postRepository.delete(savedPostId);

		// then
		assertThatThrownBy(() -> postRepository.findById(savedPostId))
			.isInstanceOf(NoSuchElementException.class);
	}

	private Post createPost(String title) {
		return Post.builder()
			.title(title)
			.viewCount(0)
			.userId(1L)
			.build();
	}
}

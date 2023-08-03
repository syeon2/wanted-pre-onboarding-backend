package wanted.assignment.post.dao.mybatis;

import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import wanted.assignment.TestBaseConfig;
import wanted.assignment.post.dao.PostDetailRepository;
import wanted.assignment.post.dao.domain.PostDetail;

class MyBatisPostDetailRepositoryTest extends TestBaseConfig {

	@Autowired
	private PostDetailRepository postDetailRepository;

	@Test
	@DisplayName("게시글에 해당하는 내용을 저장합니다.")
	void save() {
		// given
		PostDetail postDetail = createPostDetail(1L, "hello");

		// when
		Long savedPostDetailId = postDetailRepository.save(postDetail);

		// then
		assertThat(savedPostDetailId).isEqualTo(1L);
	}

	@Test
	@DisplayName("게시글에 해당하는 내용을 찾습니다.")
	void findById() {
		// given
		String content = "hello";
		PostDetail postDetail = createPostDetail(1L, content);
		Long savedPostDetailId = postDetailRepository.save(postDetail);

		assertThat(savedPostDetailId).isEqualTo(1L);

		// when
		PostDetail findPostDetail = postDetailRepository.findById(savedPostDetailId);

		// then
		assertThat(findPostDetail)
			.extracting("postId", "content")
			.contains(savedPostDetailId, content);
	}

	@Test
	@DisplayName("게시글을 수정합니다.")
	void update() {
		// given
		String content1 = "hello";
		PostDetail postDetail1 = createPostDetail(1L, content1);
		Long savedPostDetailId = postDetailRepository.save(postDetail1);

		assertThat(savedPostDetailId).isEqualTo(1L);

		// when
		String content2 = "bye";
		PostDetail postDetail2 = createPostDetail(1L, content2);

		postDetailRepository.update(savedPostDetailId, postDetail2);

		// then
		PostDetail findPostDetail = postDetailRepository.findById(savedPostDetailId);

		assertThat(findPostDetail)
			.extracting("postId", "content")
			.contains(savedPostDetailId, content2);
	}

	@Test
	@DisplayName("게시글을 삭제합니다.")
	void delete() {
		// given
		PostDetail postDetail = createPostDetail(1L, "hello");
		Long savedPostDetailId = postDetailRepository.save(postDetail);

		// when
		postDetailRepository.delete(savedPostDetailId);

		// then
		assertThatThrownBy(() -> postDetailRepository.findById(savedPostDetailId))
			.isInstanceOf(NoSuchElementException.class);
	}

	private PostDetail createPostDetail(long postId, String content) {
		return PostDetail.builder()
			.postId(postId)
			.content(content)
			.build();
	}
}

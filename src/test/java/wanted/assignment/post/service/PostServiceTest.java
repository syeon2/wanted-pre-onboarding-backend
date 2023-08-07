package wanted.assignment.post.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import wanted.assignment.TestBaseConfig;
import wanted.assignment.member.dao.MemberRepository;
import wanted.assignment.member.dao.domain.Member;
import wanted.assignment.post.dao.PostRepository;
import wanted.assignment.post.dao.domain.PostSimple;
import wanted.assignment.post.service.request.PostCreateServiceRequest;
import wanted.assignment.post.service.request.PostUpdateServiceRequest;
import wanted.assignment.post.service.response.PostResponse;

class PostServiceTest extends TestBaseConfig {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PostService postService;

	@Test
	@DisplayName("게시글을 생성합니다.")
	void createPost() {
		// given
		memberRepository.save(Member.builder().email("1234@4567").password("12345678").build());
		PostCreateServiceRequest request = createPostRequest("title1", "content입니다.");

		// when
		Long postId = postService.createPost(request);

		// then
		assertThat(postId).isEqualTo(1L);
	}

	@Test
	@DisplayName("게시글 생성 중 예외가 발생하면 롤백합니다.")
	void createPostException() {
		// given
		PostCreateServiceRequest request = createPostRequest("title1", null);

		// when
		assertThatThrownBy(() -> postService.createPost(request))
			.isInstanceOf(RuntimeException.class);

		// then
		assertThatThrownBy(() -> postRepository.findById(1L))
			.isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("커서 페이지네이션이 적용되어 모든 게시글 중 10개로 제한하여 페이지네이션합니다.")
	void findAllLimit10() {
		// given
		memberRepository.save(Member.builder().email("1234@4567").password("12345678").build());
		PostCreateServiceRequest request1 = createPostRequest("title1", "content입니다.1");
		PostCreateServiceRequest request2 = createPostRequest("title1", "content입니다.2");
		PostCreateServiceRequest request3 = createPostRequest("title1", "content입니다.3");
		PostCreateServiceRequest request4 = createPostRequest("title1", "content입니다.4");
		PostCreateServiceRequest request5 = createPostRequest("title1", "content입니다.5");
		PostCreateServiceRequest request6 = createPostRequest("title1", "content입니다.6");
		PostCreateServiceRequest request7 = createPostRequest("title1", "content입니다.7");
		PostCreateServiceRequest request8 = createPostRequest("title1", "content입니다.8");
		PostCreateServiceRequest request9 = createPostRequest("title1", "content입니다.9");
		PostCreateServiceRequest request10 = createPostRequest("title1", "content입니다.10");
		PostCreateServiceRequest request11 = createPostRequest("title1", "content입니다.11");

		Long postId1 = postService.createPost(request1);
		Long postId2 = postService.createPost(request2);
		Long postId3 = postService.createPost(request3);
		Long postId4 = postService.createPost(request4);
		Long postId5 = postService.createPost(request5);
		Long postId6 = postService.createPost(request6);
		Long postId7 = postService.createPost(request7);
		Long postId8 = postService.createPost(request8);
		Long postId9 = postService.createPost(request9);
		Long postId10 = postService.createPost(request10);
		Long postId11 = postService.createPost(request11);

		// when
		List<PostSimple> postList = postService.findPostList(1L);

		// then
		assertThat(postList).hasSize(10)
			.extracting("id", "title", "viewCount", "userId")
			.containsExactlyInAnyOrder(
				tuple(1L, "title1", 0, 1L),
				tuple(2L, "title1", 0, 1L),
				tuple(3L, "title1", 0, 1L),
				tuple(4L, "title1", 0, 1L),
				tuple(5L, "title1", 0, 1L),
				tuple(6L, "title1", 0, 1L),
				tuple(7L, "title1", 0, 1L),
				tuple(8L, "title1", 0, 1L),
				tuple(9L, "title1", 0, 1L),
				tuple(10L, "title1", 0, 1L)
			);
	}

	@Test
	@DisplayName("게시글 아이디를 사용하여 특정 게시글을 조회합니다.")
	void findPost() {
		// given
		memberRepository.save(Member.builder().email("1234@4567").password("12345678").build());
		String title = "title1";
		String content = "content입니다.";
		PostCreateServiceRequest request = createPostRequest(title, content);
		Long postId = postService.createPost(request);

		// when
		PostResponse response = postService.findPost(postId);

		// then
		assertThat(response)
			.extracting("id", "title", "viewCount", "userId", "content")
			.contains(postId, title, 0, 1L, content);
	}

	@Test
	@DisplayName("게시글 아이디를 사용하여 해당 게시글을 수정합니다.")
	void updatePost() {
		// given
		memberRepository.save(Member.builder().email("1234@4567").password("12345678").build());
		String title1 = "title1";
		String content1 = "content입니다.";
		PostCreateServiceRequest request1 = createPostRequest(title1, content1);
		Long postId = postService.createPost(request1);

		// when
		String title2 = "title2";
		String content2 = "content입니다.2";
		PostUpdateServiceRequest updateRequest = PostUpdateServiceRequest.builder()
			.title(title2)
			.content(content2)
			.build();

		Long updatePostId = postService.updatePost(postId, updateRequest);

		// then
		assertThat(updatePostId).isEqualTo(postId);

		PostResponse response = postService.findPost(updatePostId);
		assertThat(response)
			.extracting("id", "title", "viewCount", "userId", "content")
			.contains(postId, title2, 0, 1L, content2);
	}

	@Test
	@DisplayName("게시글을 수정할 때 예외가 발생하면 롤백합니다.")
	void updatePostException() {
		// given
		memberRepository.save(Member.builder().email("1234@4567").password("12345678").build());
		String title1 = "title1";
		String content1 = "content입니다.";
		PostCreateServiceRequest request1 = createPostRequest(title1, content1);
		Long postId = postService.createPost(request1);

		// when
		String title2 = "title2";
		PostUpdateServiceRequest updateRequest = PostUpdateServiceRequest.builder()
			.title(title2)
			.content(null)
			.build();

		assertThatThrownBy(() -> postService.updatePost(postId, updateRequest))
			.isInstanceOf(RuntimeException.class);

		// then
		PostResponse response = postService.findPost(postId);

		assertThat(response)
			.extracting("id", "title", "viewCount", "userId", "content")
			.contains(postId, title1, 0, 1L, content1);
	}

	@Test
	@DisplayName("게시글 아이디를 사용하여 해당 게시글을 삭제합니다.")
	void deletePost() {
		// given
		memberRepository.save(Member.builder().email("1234@4567").password("12345678").build());
		String title1 = "title1";
		String content1 = "content입니다.";
		PostCreateServiceRequest request1 = createPostRequest(title1, content1);
		Long postId = postService.createPost(request1);

		assertThat(postRepository.findAll(postId)).hasSize(1);

		// when
		postService.delete(postId);

		// then
		assertThatThrownBy(() -> postRepository.findById(postId))
			.isInstanceOf(NoSuchElementException.class);
	}

	private PostCreateServiceRequest createPostRequest(String title, String content) {
		return PostCreateServiceRequest.builder()
			.title(title)
			.userId(1L)
			.content(content)
			.build();
	}
}

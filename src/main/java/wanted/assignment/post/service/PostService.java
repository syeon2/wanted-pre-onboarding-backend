package wanted.assignment.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wanted.assignment.member.dao.MemberRepository;
import wanted.assignment.post.dao.PostDetailRepository;
import wanted.assignment.post.dao.PostRepository;
import wanted.assignment.post.dao.domain.Post;
import wanted.assignment.post.dao.domain.PostDetail;
import wanted.assignment.post.service.request.PostCreateServiceRequest;
import wanted.assignment.post.service.request.PostUpdateServiceRequest;
import wanted.assignment.post.service.response.PostResponse;

@Service
@RequiredArgsConstructor
public class PostService {

	private final MemberRepository memberRepository;
	private final PostRepository postRepository;
	private final PostDetailRepository postDetailRepository;

	@Transactional
	public Long createPost(PostCreateServiceRequest request) {
		isExistMemberId(request.getUserId());

		Long savedPostId = postRepository.save(Post.createFromServiceRequest(request));
		PostDetail postDetail = PostDetail.createPostDetailFromServiceRequest(request, savedPostId);

		postDetailRepository.save(postDetail);

		return savedPostId;
	}

	public List<Post> findPostList(Long id) {
		return postRepository.findAll(id);
	}

	public PostResponse findPost(Long id) {
		Post findPost = postRepository.findById(id);
		PostDetail findPostDetail = postDetailRepository.findById(id);

		return PostResponse.of(findPost, findPostDetail);
	}

	@Transactional
	public Long updatePost(Long id, PostUpdateServiceRequest request) {
		Long updatedPostId = postRepository.update(id, Post.updateFromServiceRequest(request));
		postDetailRepository.update(id, PostDetail.updatePostDetailFromServiceRequest(request));

		return updatedPostId;
	}

	@Transactional
	public void delete(Long id) {
		postRepository.delete(id);
		postDetailRepository.delete(id);
	}

	private void isExistMemberId(Long userId) {
		memberRepository.findById(userId);
	}
}

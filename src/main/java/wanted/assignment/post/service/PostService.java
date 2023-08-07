package wanted.assignment.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wanted.assignment.comment.CommentRepository;
import wanted.assignment.member.dao.MemberRepository;
import wanted.assignment.post.dao.PostRepository;
import wanted.assignment.post.dao.domain.PostDetail;
import wanted.assignment.post.dao.domain.PostSimple;
import wanted.assignment.post.service.request.PostCreateServiceRequest;
import wanted.assignment.post.service.request.PostUpdateServiceRequest;
import wanted.assignment.post.service.response.PostResponse;

@Service
@RequiredArgsConstructor
public class PostService {

	private final MemberRepository memberRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	public Long createPost(PostCreateServiceRequest request) {
		isExistMemberId(request.getUserId());

		return postRepository.save(PostDetail.createFromServiceRequest(request));
	}

	public List<PostSimple> findPostList(Long id) {
		return postRepository.findAll(id);
	}

	public PostResponse findPost(Long id) {
		PostDetail findPost = postRepository.findById(id);

		return PostResponse.of(findPost);
	}

	public Long updatePost(Long id, PostUpdateServiceRequest request) {
		return postRepository.update(id, PostDetail.updateFromServiceRequest(request));
	}

	@Transactional
	public void delete(Long id) {
		postRepository.delete(id);
		commentRepository.delete(id);
	}

	private void isExistMemberId(Long userId) {
		memberRepository.findById(userId);
	}
}

package wanted.assignment.post.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wanted.assignment.common.annotation.JwtAuthorization;
import wanted.assignment.common.basewrapper.ApiResult;
import wanted.assignment.common.config.jwt.MemberTokenInfo;
import wanted.assignment.common.error.exception.jwt.CustomJwtTokenException;
import wanted.assignment.post.dao.domain.PostSimple;
import wanted.assignment.post.service.PostService;
import wanted.assignment.post.service.response.PostResponse;
import wanted.assignment.post.web.request.PostCreateRequest;
import wanted.assignment.post.web.request.PostUpdateRequest;

@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping("/api/v1/post")
	public ApiResult<Long> createPost(@Valid @RequestBody PostCreateRequest request) {
		Long createPostId = postService.createPost(request.toServiceRequest());
		return ApiResult.onSuccess(createPostId);
	}

	@GetMapping("/api/v1/post/{id}")
	public ApiResult<PostResponse> findPostById(@PathVariable Long id) {
		PostResponse response = postService.findPost(id);
		return ApiResult.onSuccess(response);
	}

	@GetMapping("/api/v1/post-list")
	public ApiResult<List<PostSimple>> findPostList(@RequestParam Long limit) {
		List<PostSimple> postList = postService.findPostList(limit);
		return ApiResult.onSuccess(postList);
	}

	@PostMapping("/api/v1/post/edit")
	public ApiResult<Long> updatePost(
		@Valid @RequestBody PostUpdateRequest request,
		@JwtAuthorization MemberTokenInfo memberTokenInfo
	) {
		checkAuthority(request.getPostId(), memberTokenInfo, "게시글을 수정할 권한이 없습니다.");

		Long updatePostId = postService.updatePost(request.getPostId(), request.toServiceRequest());

		return ApiResult.onSuccess(updatePostId);
	}

	@DeleteMapping("/api/v1/post/{id}")
	public ApiResult<Long> deletePost(
		@PathVariable Long id,
		@JwtAuthorization MemberTokenInfo memberTokenInfo
	) {
		checkAuthority(id, memberTokenInfo, "게시글을 삭제할 권한이 없습니다.");

		postService.delete(id);

		return ApiResult.onSuccess(id);
	}

	private void checkAuthority(Long request, MemberTokenInfo memberTokenInfo, String message) {
		PostResponse response = postService.findPost(request);

		if (!response.getUserId().equals(memberTokenInfo.getId())) {
			throw new CustomJwtTokenException(message);
		}
	}
}

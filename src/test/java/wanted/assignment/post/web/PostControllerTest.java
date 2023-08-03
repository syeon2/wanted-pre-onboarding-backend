package wanted.assignment.post.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import wanted.assignment.common.config.jwt.JwtAuthTokenProvider;
import wanted.assignment.common.config.jwt.MemberTokenInfo;
import wanted.assignment.common.error.exception.jwt.CustomJwtTokenException;
import wanted.assignment.common.generator.TimeGenerator;
import wanted.assignment.post.dao.domain.Post;
import wanted.assignment.post.service.PostService;
import wanted.assignment.post.service.response.PostResponse;
import wanted.assignment.post.web.request.PostCreateRequest;
import wanted.assignment.post.web.request.PostUpdateRequest;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PostService postService;

	@MockBean
	private JwtAuthTokenProvider jwtAuthTokenProvider;

	@Test
	@DisplayName("api를 호출하여 게시글을 생성합니다.")
	void createPost() throws Exception {
		// given
		PostCreateRequest request = PostCreateRequest.builder()
			.title("제목")
			.userId(1L)
			.content("내용!")
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/post")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시글을 생성할 때 제목은 빈칸을 허용하지 않습니다.")
	void createPostWithoutTitle() throws Exception {
		// given
		PostCreateRequest request = PostCreateRequest.builder()
			.title("   ")
			.userId(1L)
			.content("내용!")
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/post")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("제목은 빈칸을 허용하지 않습니다."));
	}

	@Test
	@DisplayName("게시글을 생성할 때 회원 고유 번호는 필수 값입니다.")
	void createPostWithoutUserId() throws Exception {
		// given
		PostCreateRequest request = PostCreateRequest.builder()
			.title("제목!")
			.content("내용!")
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/post")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("회원 고유번호는 필수값입니다."));
	}

	@Test
	@DisplayName("게시글을 생성할 때 게시글 내용은 빈칸을 허용하지 않습니다.")
	void createPostWithoutContent() throws Exception {
		// given
		PostCreateRequest request = PostCreateRequest.builder()
			.title("제목!")
			.userId(1L)
			.content("     ")
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/post")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("게시글 내용은 빈칸을 허용하지 않습니다."));
	}

	@Test
	@DisplayName("게시글 아이디를 사용하여 특정 게시글을 조회합니다.")
	void findPostById() throws Exception {
		// given
		long postId = 1L;

		PostResponse response = PostResponse.builder()
			.id(postId)
			.title("제목!")
			.viewCount(0)
			.userId(1L)
			.content("내용!")
			.build();

		when(postService.findPost(postId)).thenReturn(response);

		// when // then
		mockMvc.perform(
				get("/api/v1/post/" + postId)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isNotEmpty())
			.andExpect(jsonPath("$.message").doesNotExist());
	}

	@Test
	@DisplayName("특정 게시글을 기준으로 게시글 리스트를 조회합니다. (커서 페이지네이션)")
	void findPostList() throws Exception {
		// given
		long postId = 1L;
		List<Post> postList = List.of();

		when(postService.findPostList(postId)).thenReturn(postList);

		// when // then
		mockMvc.perform(
				get("/api/v1/post-list?limit=" + postId)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.message").doesNotExist());
	}

	@Test
	@DisplayName("게시글의 제목과 내용을 업데이트합니다.")
	void updatePost() throws Exception {
		// given
		long postId = 1L;
		String email = "1234@5678";
		when(postService.findPost(postId)).thenReturn(
			PostResponse.builder().title("원래").userId(1L).content("기본").build());
		String jwtAuthToken = getJwtToken(1L, email);
		MemberTokenInfo memberTokenInfo = parsingTokenToMember(jwtAuthToken);

		PostUpdateRequest request = PostUpdateRequest.builder()
			.postId(postId)
			.title("갱신!")
			.content("내용 변경!")
			.build();

		when(postService.updatePost(postId, request.toServiceRequest())).thenReturn(postId);
		when(jwtAuthTokenProvider.validateToken(jwtAuthToken)).thenReturn(true);
		when(jwtAuthTokenProvider.parsingTokenToMember(jwtAuthToken)).thenReturn(
			MemberTokenInfo.builder().id(1L).email(email).build());

		// when  // then
		mockMvc.perform(
				post("/api/v1/post/edit")
					.header("x-auth-token", jwtAuthToken)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isNotEmpty())
			.andExpect(jsonPath("$.message").doesNotExist());
	}

	@Test
	@DisplayName("게시물 수정할 권한이 없으면 예외를 던집니다. (validation에서 JwtToken 확인)")
	void updatePostWithoutAuthority() throws Exception {
		// given
		long postId = 1L;
		String email = "1234@5678";
		when(postService.findPost(postId)).thenReturn(
			PostResponse.builder().title("원래").userId(1L).content("기본").build());
		String jwtAuthToken = getJwtToken(1L, email);
		MemberTokenInfo memberTokenInfo = parsingTokenToMember(jwtAuthToken);

		PostUpdateRequest request = PostUpdateRequest.builder()
			.postId(postId)
			.title("갱신!")
			.content("내용 변경!")
			.build();

		when(postService.updatePost(postId, request.toServiceRequest())).thenReturn(postId);
		when(jwtAuthTokenProvider.validateToken(jwtAuthToken)).thenReturn(false);

		// when  // then
		mockMvc.perform(
				post("/api/v1/post/edit")
					.header("x-auth-token", jwtAuthToken)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("토큰 검증 실패"));
	}

	@Test
	@DisplayName("게시물 수정할 권한이 없으면 예외를 던집니다. (Token의 아이디와 게시물 아이디와 다른 경우)")
	void updatePostDiffMemberId() throws Exception {
		// given
		Long postId = 1L;

		Long userId1 = 1L;
		String userEmail1 = "1234@5678";
		when(postService.findPost(postId)).thenReturn(
			PostResponse.builder().title("기존 제목").userId(userId1).content("기존 내용").build());

		Long userId2 = 2L;
		String userEmail2 = "abcd@efgh";
		String jwtAuthToken = getJwtToken(userId2, userEmail2);
		MemberTokenInfo memberTokenInfo = parsingTokenToMember(jwtAuthToken);

		PostUpdateRequest request = PostUpdateRequest.builder()
			.postId(postId)
			.title("제목 변경")
			.content("내용 변경")
			.build();

		when(postService.updatePost(postId, request.toServiceRequest())).thenReturn(postId);
		when(jwtAuthTokenProvider.validateToken(jwtAuthToken)).thenReturn(true);
		when(jwtAuthTokenProvider.parsingTokenToMember(jwtAuthToken)).thenReturn(
			MemberTokenInfo.builder().id(userId2).email(userEmail1).build());

		// when  // then
		mockMvc.perform(
				post("/api/v1/post/edit")
					.header("x-auth-token", jwtAuthToken)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("게시글을 수정할 권한이 없습니다."));
	}

	@Test
	@DisplayName("게시물을 삭제합니다.")
	void deletePost() throws Exception {
		// given
		Long postId = 1L;

		Long userId = 1L;
		String email = "1234@5678";
		when(postService.findPost(postId)).thenReturn(
			PostResponse.builder().title("기존 제목").userId(userId).content("기존 내용").build());

		String jwtAuthToken = getJwtToken(userId, email);
		MemberTokenInfo memberTokenInfo = parsingTokenToMember(jwtAuthToken);
		
		when(jwtAuthTokenProvider.validateToken(jwtAuthToken)).thenReturn(true);
		when(jwtAuthTokenProvider.parsingTokenToMember(jwtAuthToken)).thenReturn(
			MemberTokenInfo.builder().id(1L).email(email).build());

		// when  // then
		mockMvc.perform(
				delete("/api/v1/post/" + postId)
					.header("x-auth-token", jwtAuthToken)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(postId))
			.andExpect(jsonPath("$.message").doesNotExist());
	}

	private String getJwtToken(Long id, String email) {
		Claims claims = Jwts.claims();
		claims.put("id", id.toString());
		claims.put("email", email);

		return Jwts.builder()
			.setHeaderParam("typ", "JWT")
			.setClaims(claims)
			.signWith(getHashingKey(), SignatureAlgorithm.HS256)
			.setExpiration(TimeGenerator.getTimeInFuture(30))
			.compact();
	}

	private MemberTokenInfo parsingTokenToMember(String token) {
		try {
			Claims body = Jwts.parserBuilder()
				.setSigningKey(getHashingKey()).build()
				.parseClaimsJws(removeBearer(token))
				.getBody();

			Long id = Long.parseLong(body.get("id", String.class));
			String email = body.get("email", String.class);

			return MemberTokenInfo.builder()
				.id(id)
				.email(email)
				.build();
		} catch (RuntimeException e) {
			throw new CustomJwtTokenException("토큰 검증 실패");
		}
	}

	private SecretKey getHashingKey() {
		return Keys.hmacShaKeyFor(
			"88359e9d7aa4d2cbba413a33a4babfcde0cf243406f60f1f044c4a58860d1708".getBytes(StandardCharsets.UTF_8));
	}

	private String removeBearer(String token) {
		return token.replace("Bearer", "").trim();
	}
}

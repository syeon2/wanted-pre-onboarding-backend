package wanted.assignment.member.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import wanted.assignment.common.config.jwt.JwtAuthTokenProvider;
import wanted.assignment.member.dao.domain.Member;
import wanted.assignment.member.service.MemberService;
import wanted.assignment.member.web.request.MemberSignInRequest;
import wanted.assignment.member.web.request.MemberSignUpRequest;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MemberService memberService;

	@MockBean
	private JwtAuthTokenProvider jwtAuthTokenProvider;

	@Test
	@DisplayName("회원을 등록합니다.")
	void joinMember() throws Exception {
		// given
		MemberSignUpRequest request = MemberSignUpRequest.builder()
			.email("xxxxx@yyyyy")
			.password("yyyyyyyyyyy")
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/sign-up")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("회원 가입에는 이메일이 필수입니다.")
	void joinMemberWithoutEmail() throws Exception {
		// given
		MemberSignUpRequest request = MemberSignUpRequest.builder()
			.password("yyyyyyyy")
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/sign-up")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("이메일은 필수값입니다."));
	}

	@Test
	@DisplayName("회원 가입 시 이메일 형식을 지켜야합니다. (@ 포함)")
	void joinMemberWithoutEmailFormat() throws Exception {
		// given
		MemberSignUpRequest request = MemberSignUpRequest.builder()
			.email("xxxxxxx")
			.password("yyyyyyyy")
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/sign-up")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("올바른 이메일 형식으로 입력해주세요."));
	}

	@Test
	@DisplayName("비밀번호는 8자 이상 입력해야 합니다.")
	void joinMemberWithoutPasswordFormat() throws Exception {
		// given
		MemberSignUpRequest request = MemberSignUpRequest.builder()
			.email("xxxxxxx@yyyyyyyyy")
			.password("yy")
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/sign-up")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("비밀번호는 8자 이상 입력해주세요."));
	}

	@Test
	@DisplayName("회원가입한 회원은 로그인이 정상처리됩니다.")
	void signInMember() throws Exception {
		// given
		String email = "xxxxx@yyyyy";
		String password = "yyyyyyyyyyy";

		MemberSignInRequest request = MemberSignInRequest.builder()
			.email(email)
			.password(password)
			.build();

		Member responseMember = Member.builder().email(email).password(password).build();
		when(memberService.login(email, password)).thenReturn(responseMember);

		// when // then
		mockMvc.perform(
				post("/api/v1/sign-in")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").doesNotExist())
			.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	@DisplayName("로그인 시 아이디는 필수입니다.")
	void signInMemberWithoutEmail() throws Exception {
		// given
		String password = "yyyyyyyyyyy";

		MemberSignInRequest request = MemberSignInRequest.builder()
			.password(password)
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/sign-in")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("이메일은 필수값입니다."));
	}

	@Test
	@DisplayName("로그인 시 아이디를 올바른 이메일 형식으로 입력해주세요.")
	void signInMemberWrongEmailFormat() throws Exception {
		// given
		String email = "xxxxxxx";
		String password = "yyyyyyyyyyy";

		MemberSignInRequest request = MemberSignInRequest.builder()
			.email(email)
			.password(password)
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/sign-in")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("올바른 이메일 형식으로 입력해주세요."));
	}

	@Test
	@DisplayName("로그인 시 비밀번호를 8자 이상 입력해주세요.")
	void signInMemberWrongPassword() throws Exception {
		// given
		String email = "xxxxxxx@yyyyyy.com";
		String password = "yyy";

		MemberSignInRequest request = MemberSignInRequest.builder()
			.email(email)
			.password(password)
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/sign-in")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("비밀번호는 8자 이상 입력해주세요."));
	}
}

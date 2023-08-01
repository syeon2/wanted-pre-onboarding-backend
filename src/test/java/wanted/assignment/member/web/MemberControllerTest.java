package wanted.assignment.member.web;

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

import wanted.assignment.member.service.MemberService;
import wanted.assignment.member.web.request.MemberCreateRequest;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MemberService memberService;

	@Test
	@DisplayName("회원을 등록합니다.")
	void joinMember() throws Exception {
		// given
		MemberCreateRequest request = MemberCreateRequest.builder()
			.email("xxxxx@yyyyy")
			.password("yyyyyyyyyyy")
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/member")
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
		MemberCreateRequest request = MemberCreateRequest.builder()
			.password("yyyyyyyy")
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/member")
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
		MemberCreateRequest request = MemberCreateRequest.builder()
			.email("xxxxxxx")
			.password("yyyyyyyy")
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/member")
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
		MemberCreateRequest request = MemberCreateRequest.builder()
			.email("xxxxxxx@yyyyyyyyy")
			.password("yy")
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/member")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("비밀번호는 8자 이상 입력해주세요."));
	}
}

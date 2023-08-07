package wanted.assignment.member.web;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wanted.assignment.common.basewrapper.ApiResult;
import wanted.assignment.common.config.jwt.JwtAuthTokenProvider;
import wanted.assignment.member.dao.domain.Member;
import wanted.assignment.member.service.MemberService;
import wanted.assignment.member.web.request.MemberSignInRequest;
import wanted.assignment.member.web.request.MemberSignUpRequest;
import wanted.assignment.member.web.response.MemberSignInResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final JwtAuthTokenProvider jwtAuthTokenProvider;

	@PostMapping("/api/v1/sign-up")
	public ApiResult<Long> signUpMember(@Valid @RequestBody MemberSignUpRequest request) {
		Long joinedMemberId = memberService.join(request.toServiceRequest());
		return ApiResult.onSuccess(joinedMemberId);
	}

	@PostMapping("/api/v1/sign-in")
	public ApiResult<MemberSignInResponse> signInMember(@Valid @RequestBody MemberSignInRequest request) {
		Member member = memberService.login(request.getEmail(), request.getPassword());
		String jwtAuthToken = jwtAuthTokenProvider.createJwtAuthToken(member.getId(), member.getEmail());

		MemberSignInResponse response = member.getSignInResponse(jwtAuthToken);

		return ApiResult.onSuccess(response);
	}
}

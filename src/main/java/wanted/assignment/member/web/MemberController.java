package wanted.assignment.member.web;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wanted.assignment.common.basewrapper.ApiResult;
import wanted.assignment.member.service.MemberService;
import wanted.assignment.member.web.request.MemberCreateRequest;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/api/v1/member")
	public ApiResult<Long> joinMember(@Valid @RequestBody MemberCreateRequest request) {
		Long joinedMemberId = memberService.join(request.toServiceRequest());
		return ApiResult.onSuccess(joinedMemberId);
	}
}

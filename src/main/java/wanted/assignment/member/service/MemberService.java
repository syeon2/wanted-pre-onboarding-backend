package wanted.assignment.member.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wanted.assignment.member.dao.MemberRepository;
import wanted.assignment.member.dao.domain.Member;
import wanted.assignment.member.service.request.MemberCreateServiceRequest;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Long join(MemberCreateServiceRequest request) {
		Member member = Member.createFromServiceRequest(request);
		return memberRepository.save(member);
	}
}

package wanted.assignment.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wanted.assignment.common.error.exception.member.DuplicateEmailException;
import wanted.assignment.member.dao.MemberRepository;
import wanted.assignment.member.dao.domain.Member;
import wanted.assignment.member.service.request.MemberCreateServiceRequest;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Long join(MemberCreateServiceRequest request) {
		boolean isDuplicateEmail = checkDuplicateEmail(request.getEmail());

		if (isDuplicateEmail) {
			throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
		}

		Member member = Member.createFromServiceRequest(request);
		return memberRepository.save(member);
	}

	private boolean checkDuplicateEmail(String email) {
		Optional<Member> findEmailOptional = memberRepository.findByEmail(email);

		return findEmailOptional.isPresent();
	}
}

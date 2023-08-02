package wanted.assignment.member.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wanted.assignment.common.error.exception.member.DuplicateEmailException;
import wanted.assignment.common.error.exception.member.PasswordMismatchException;
import wanted.assignment.common.generator.Sha256Util;
import wanted.assignment.member.dao.MemberRepository;
import wanted.assignment.member.dao.domain.Member;
import wanted.assignment.member.service.request.MemberCreateServiceRequest;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Long join(MemberCreateServiceRequest request) {
		checkDuplicateEmail(request.getEmail());

		Member member = Member.createFromServiceRequest(request);
		return memberRepository.save(member);
	}

	public Member login(String email, String password) {
		Member member = getMemberByEmail(email);
		checkPassword(member, password);

		return member;
	}

	private void checkDuplicateEmail(String email) {
		Optional<Member> findEmailOptional = memberRepository.findByEmail(email);

		if (findEmailOptional.isPresent()) {
			throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
		}
	}

	private Member getMemberByEmail(String email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 아이디입니다."));
	}

	private void checkPassword(Member member, String password) {
		String encryptPassword = Sha256Util.getEncrypt(password);

		if (!member.getPassword().equals(encryptPassword)) {
			throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
		}
	}
}

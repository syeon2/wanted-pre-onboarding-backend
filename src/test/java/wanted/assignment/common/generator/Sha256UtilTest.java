package wanted.assignment.common.generator;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Sha256UtilTest {

	@Test
	@DisplayName("단방향 암호화 방식 - 하나의 비밀번호를 여러번 암호화해도 같은 값을 가집니다.")
	void getEncrypt() {
		// given
		String password = "password";

		// when
		String encrypt1 = Sha256Util.getEncrypt(password);
		String encrypt2 = Sha256Util.getEncrypt(password);

		// then
		assertThat(encrypt1).isEqualTo(encrypt2);
	}
}

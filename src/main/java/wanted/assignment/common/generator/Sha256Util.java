package wanted.assignment.common.generator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sha256Util {

	private static final String ENCRYPTION_TYPE = "SHA-256";

	public static String getEncrypt(String source) {
		String salt = generateSalt();

		return getEncrypt(source, salt.getBytes());
	}

	private static String getEncrypt(String source, byte[] salt) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION_TYPE);
			byte[] bytes = new byte[source.getBytes().length + salt.length];
			byte[] byteData = messageDigest.digest(bytes);

			return IntStream.range(0, byteData.length)
				.mapToObj(n -> String.format("%02x", byteData[n]))
				.collect(Collectors.joining());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 암호화 에러");
		}
	}

	private static String generateSalt() {
		ThreadLocalRandom random = ThreadLocalRandom.current();

		byte[] salt = new byte[8];
		random.nextBytes(salt);

		StringBuffer sb = new StringBuffer();
		for (byte b : salt) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}
}

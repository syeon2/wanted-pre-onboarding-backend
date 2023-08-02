package wanted.assignment.common.config.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import wanted.assignment.common.error.exception.jwt.CustomJwtTokenException;
import wanted.assignment.common.generator.TimeGenerator;

@Slf4j
@Getter
@Component
public class JwtAuthTokenProvider {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expired_minutes}")
	private Integer expiredMinutes;

	public boolean validateToken(String token) {
		try {
			return !Jwts.parserBuilder()
				.setSigningKey(getHashingKey())
				.build()
				.parseClaimsJws(removeBearer(token))
				.getBody()
				.getExpiration().before(new Date());
		} catch (Exception e) {
			throw new CustomJwtTokenException("토큰 검증 실패");
		}
	}

	public MemberTokenInfo parsingTokenToMember(String token) {
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

	public String createJwtAuthToken(Long id, String email) {
		Claims claims = Jwts.claims();
		claims.put("id", id.toString());
		claims.put("email", email);

		return Jwts.builder()
			.setHeaderParam("typ", "JWT")
			.setClaims(claims)
			.signWith(getHashingKey(), SignatureAlgorithm.HS256)
			.setExpiration(TimeGenerator.getTimeInFuture(expiredMinutes))
			.compact();
	}

	private String removeBearer(String token) {
		return token.replace("Bearer", "").trim();
	}

	private SecretKey getHashingKey() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}
}

package wanted.assignment.common.config.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;
import wanted.assignment.common.annotation.JwtAuthorization;
import wanted.assignment.common.error.exception.jwt.CustomJwtTokenException;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationArgumentResolver implements HandlerMethodArgumentResolver {

	@Value("${jwt.header}")
	private String header;

	private final JwtAuthTokenProvider jwtAuthTokenProvider;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(JwtAuthorization.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

		HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

		if (httpServletRequest != null) {
			String token = httpServletRequest.getHeader(header);

			if (jwtAuthTokenProvider.validateToken(token)) {
				return jwtAuthTokenProvider.parsingTokenToMember(token);
			}
		}

		throw new CustomJwtTokenException("토큰 검증 실패");
	}
}

package wanted.assignment.common.error.exception.jwt;

public class CustomJwtTokenException extends RuntimeException {
	public CustomJwtTokenException(String message) {
		super(message);
	}
}

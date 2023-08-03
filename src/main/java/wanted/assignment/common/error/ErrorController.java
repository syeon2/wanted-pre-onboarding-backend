package wanted.assignment.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import wanted.assignment.common.basewrapper.ApiResult;
import wanted.assignment.common.error.exception.jwt.CustomJwtTokenException;
import wanted.assignment.common.error.exception.member.DuplicateEmailException;
import wanted.assignment.common.error.exception.member.PasswordMismatchException;

@RestControllerAdvice
public class ErrorController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({DuplicateEmailException.class, PasswordMismatchException.class})
	public ApiResult<Void> handlerDuplicateEmailException(RuntimeException e) {
		return ApiResult.onFailure(e.getLocalizedMessage());
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(CustomJwtTokenException.class)
	public ApiResult<Void> handlerCustomJwtTokenException(CustomJwtTokenException e) {
		return ApiResult.onFailure(e.getLocalizedMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResult<Void> handlerValidationException(MethodArgumentNotValidException e) {
		return ApiResult.onFailure(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}
}

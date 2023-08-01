package wanted.assignment.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import wanted.assignment.common.basewrapper.ApiResult;
import wanted.assignment.common.error.exception.member.DuplicateEmailException;

@RestControllerAdvice
public class ErrorController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DuplicateEmailException.class)
	public ApiResult<Void> handlerDuplicateEmailException(DuplicateEmailException e) {
		return ApiResult.onFailure(e.getLocalizedMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResult<Void> handlerValidationException(MethodArgumentNotValidException e) {
		return ApiResult.onFailure(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}
}

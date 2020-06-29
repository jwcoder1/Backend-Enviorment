package org.esy.base.handler;

import java.util.List;

import org.esy.base.common.ErrorMsg;
import org.esy.base.exception.ValidRuntimeException;
import org.esy.base.http.HttpResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public HttpResult handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			ErrorMsg.add(fieldError.getDefaultMessage());
		}
		return new HttpResult("301", ErrorMsg.getMsg());
	}

	@ExceptionHandler(ValidRuntimeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public HttpResult processUnauthenticatedException(ValidRuntimeException e) {
		return new HttpResult("301", e.getMessage());
	}
}

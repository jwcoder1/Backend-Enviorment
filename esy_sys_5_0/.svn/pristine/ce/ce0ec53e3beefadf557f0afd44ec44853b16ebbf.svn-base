package org.esy.base.util;

import org.springframework.http.HttpStatus;

public class YesException extends UnsupportedOperationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpStatus errorcode = HttpStatus.INTERNAL_SERVER_ERROR;

	public YesException() {
		super();
	}

	public YesException(HttpStatus httpStatus) {
		super(httpStatus.getReasonPhrase());
		this.errorcode = httpStatus;
	}

	public YesException(HttpStatus errorcode, String msg) {
		super(msg);
		this.errorcode = errorcode;
	}

	public HttpStatus getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(HttpStatus errorcode) {
		this.errorcode = errorcode;
	}

}

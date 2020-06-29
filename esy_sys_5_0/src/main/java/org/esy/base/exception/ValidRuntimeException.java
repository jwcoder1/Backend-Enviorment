package org.esy.base.exception;

/**
 * 
 * 效验异常
 *
 * @author cx
 * @date 2016年5月10日 下午4:35:45 
 * @version v1.0
 */
public class ValidRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 2831542037145277802L;

	public ValidRuntimeException() {
		super();
	}

	public ValidRuntimeException(String message) {
		super(message);
	}

	public ValidRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidRuntimeException(Throwable cause) {
		super(cause);
	}
}

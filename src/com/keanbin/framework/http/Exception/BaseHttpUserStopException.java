/**
 * 
 */
package com.keanbin.framework.http.Exception;

/**
 * @ClassName BaseHttpUserStopException
 * @Description 用户主动停止http请求的抛出的异常
 * @author kevin
 */
@SuppressWarnings("serial")
public class BaseHttpUserStopException extends BaseHttpException {

	public BaseHttpUserStopException(Throwable cause) {
		super(cause);
	}

	public BaseHttpUserStopException(String message) {
		super(message);
	}

	public BaseHttpUserStopException(String message, Throwable cause) {
		super(message, cause);
	}
}

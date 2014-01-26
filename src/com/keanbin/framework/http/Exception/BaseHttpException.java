/**
 * 
 */
package com.keanbin.framework.http.Exception;

/**
 * @ClassName BaseHttpException
 * @Description 基本的http异常
 * @author kevin
 */
@SuppressWarnings("serial")
public class BaseHttpException extends Exception {

	public BaseHttpException(Throwable cause) {
		super(cause);
	}

	public BaseHttpException(String message) {
		super(message);
	}

	public BaseHttpException(String message, Throwable cause) {
		super(message, cause);
	}
}

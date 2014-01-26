/**
 * 
 */
package com.keanbin.framework.http.Exception;

/**
 * @ClassName BaseHttpConnectionException
 * @Description 基本的http连接异常
 * @author kevin
 */
@SuppressWarnings("serial")
public class BaseHttpConnectionException extends BaseHttpException {

	public BaseHttpConnectionException(Throwable cause) {
		super(cause);
	}

	public BaseHttpConnectionException(String message) {
		super(message);
	}

	public BaseHttpConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}

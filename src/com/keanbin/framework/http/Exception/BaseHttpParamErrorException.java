/**
 * 
 */
package com.keanbin.framework.http.Exception;

/**
 * @ClassName BaseHttpParamErrorException
 * @Description 基本的http请求参数错误异常
 * @author kevin
 */
@SuppressWarnings("serial")
public class BaseHttpParamErrorException extends BaseHttpException {

	public BaseHttpParamErrorException(Throwable cause) {
		super(cause);
	}

	public BaseHttpParamErrorException(String message) {
		super(message);
	}

	public BaseHttpParamErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}

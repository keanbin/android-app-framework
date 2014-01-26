package com.keanbin.framework.base;

/**
 * Log 接口
 * 
 * @author keanbin
 * 
 */
public interface BaseLog {

	public void log(String msg);

	public void log(String msg, Throwable e);

	public void logError(String msg);

	public void logError(String msg, Throwable e);

	public void logError(Throwable e);
}

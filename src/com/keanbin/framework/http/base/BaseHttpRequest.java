package com.keanbin.framework.http.base;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.http.NameValuePair;

import com.keanbin.framework.base.BaseClass;
import com.keanbin.framework.http.Exception.BaseHttpConnectionException;
import com.keanbin.framework.http.Exception.BaseHttpParamErrorException;
import com.keanbin.framework.http.Exception.BaseHttpUserStopException;
import com.keanbin.framework.http.entity.DownloadFileInfo;
import com.keanbin.framework.http.entity.UploadFileInfo;

/**
 * 基本的http请求操作
 * 
 * @author keanbin
 * 
 */
public abstract class BaseHttpRequest extends BaseClass {

	/**
	 * 默认的请求超时时间10秒钟
	 */
	private static final int DEFAULT_REQUEST_TIMEOUT = 10 * 1000;

	/**
	 * 默认的上传文件请求超时时间
	 */
	private static final int DEFAULT_UPLOAD_FILE_TIMEOUT = 60 * 1000;

	/**
	 * 默认的下载文件请求超时时间
	 */
	private static final int DEFAULT_DOWNLOAD_FILE_TIMEOUT = 60 * 1000;

	/**
	 * Http请求过程中，刷新显示的默认时间间隔
	 */
	private static final long DEFAULT_HTTP_PROCESS_REFRESH_DISP_INTERVAL_TIME = 1 * 1000;

	protected int mRequestTimeOut;
	protected int mUploadFileTimeOut;
	protected int mDownloadFileTimeOut;

	/**
	 * Http请求过程中，刷新显示的时间间隔
	 */
	protected long mProcessRefreshDispIntervalTime;

	/**
	 * http请求是否停止
	 */
	protected boolean isStop;

	public BaseHttpRequest() {
		isStop = false;
		setTimeOut(DEFAULT_REQUEST_TIMEOUT, DEFAULT_UPLOAD_FILE_TIMEOUT,
				DEFAULT_DOWNLOAD_FILE_TIMEOUT);
		mProcessRefreshDispIntervalTime = DEFAULT_HTTP_PROCESS_REFRESH_DISP_INTERVAL_TIME;
	}

	/**
	 * 停止 Http 请求
	 */
	public void stop() {
		isStop = true;
	}

	/**
	 * 设置Http请求过程中刷新显示的时间间隔
	 * 
	 * @param time
	 *            间隔时间，单位是毫秒
	 */
	public void setProcessRefreshDispIntervalTime(long time) {

		if (time < 0) {
			mProcessRefreshDispIntervalTime = DEFAULT_HTTP_PROCESS_REFRESH_DISP_INTERVAL_TIME;
		} else {
			mProcessRefreshDispIntervalTime = time;
		}
	}

	/**
	 * 设置超时时间
	 * 
	 * @param requestTimeOut
	 *            正常HTTP请求的超时时间，小于0使用默认HTTP请求的超时时间。
	 * @param uploadFileTimeOut
	 *            上传文件Http请求的超时时间，小于0使用默认上传文件Http请求的超时时间。
	 * @param downloadFileTimeOut
	 *            下载文件Http请求的超时时间，小于0使用下载文件Http请求的超时时间。
	 */
	public void setTimeOut(int requestTimeOut, int uploadFileTimeOut,
			int downloadFileTimeOut) {
		if (requestTimeOut < 0) {
			mRequestTimeOut = DEFAULT_REQUEST_TIMEOUT;
		} else {
			mRequestTimeOut = requestTimeOut;
		}

		if (uploadFileTimeOut < 0) {
			mUploadFileTimeOut = DEFAULT_UPLOAD_FILE_TIMEOUT;
		} else {
			mUploadFileTimeOut = uploadFileTimeOut;
		}

		if (downloadFileTimeOut < 0) {
			mDownloadFileTimeOut = DEFAULT_DOWNLOAD_FILE_TIMEOUT;
		} else {
			mDownloadFileTimeOut = downloadFileTimeOut;
		}
	}

	/**
	 * Get方式Http请求，返回的结果是字节数组。
	 * 
	 * @param urlStr
	 *            网址
	 * @param nameValuePairs
	 *            参数
	 * @return 返回的结果是字节数组。
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpParamErrorException
	 */
	public abstract byte[] getMethod(String urlStr,
			List<NameValuePair> nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException;

	/**
	 * Post方式Http请求，返回的结果是字节数组。
	 * 
	 * @param urlStr
	 *            网址
	 * @param nameValuePairs
	 *            参数
	 * @return 返回的结果是字节数组。
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpParamErrorException
	 */
	public abstract byte[] postMethod(String urlStr,
			List<NameValuePair> nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException;

	/**
	 * 带参数，上传多个文件，返回结果的格式是字节数组。
	 * 
	 * @param uploadUrl
	 *            网址
	 * @param uploadFileInfoList
	 *            文件列表
	 * @param nameValuePairs
	 *            参数
	 * @return 返回的结果是字节数组。
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpParamErrorException
	 */
	public abstract byte[] uploadFile(String uploadUrl,
			List<UploadFileInfo> uploadFileInfoList,
			List<NameValuePair> nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException;

	/**
	 * 下载文件
	 * 
	 * @param fileUrl
	 *            文件的网址
	 * @param file
	 *            File对象
	 * @param isResume
	 *            是否断点下载
	 * @param listener
	 *            下载文件监听器
	 * @return 文件的绝对路径
	 * @throws BaseHttpParamErrorException
	 * @throws BaseHttpConnectionException
	 */
	public abstract String downLoadFile(String fileUrl, File file,
			boolean isResume, DownloadFileListener listener)
			throws BaseHttpParamErrorException, BaseHttpConnectionException,
			BaseHttpUserStopException;

	/**
	 * 把字节流中的数据存入文件中。
	 * 
	 * @param file
	 *            存入的文件
	 * @param is
	 *            输入流
	 * @param isResume
	 *            是否是断点下载
	 * @param count
	 *            文件总大小
	 * @param listener
	 *            下载文件的监听器，监听下载过程。
	 * @return
	 */
	protected boolean readInputStreamToFile(File file, InputStream is,
			boolean isResume, long count, DownloadFileListener listener) {
		if (file == null || !file.exists() || is == null) {
			return false;
		}

		boolean returnFlag = false;

		long current = 0L;
		long lastTime = 0l;
		long now = 0l;

		try {
			OutputStream output;
			if (isResume) {
				output = new FileOutputStream(file, true);
				current = file.length();
			} else {
				output = new FileOutputStream(file);
				current = 0L;
			}

			byte[] buffer = new byte[4 * 1024];
			try {
				int length;
				while ((length = (is.read(buffer))) > 0 && !isStop) {
					output.write(buffer, 0, length);

					current += length;

					if (listener != null) {
						now = System.currentTimeMillis();

						if (now - lastTime >= mProcessRefreshDispIntervalTime) {
							lastTime = now;
							listener.downloading(count, current, true);
						} else {
							listener.downloading(count, current, false);
						}
					}
				}

				listener.downloading(count, current, true);

				// 删除下载信息的信息文件
				if (count == current) {
					DownloadFileInfo.deleteDownloadInfoFile(file
							.getAbsolutePath());
				}

				output.flush();
				returnFlag = true;
			} catch (IOException e) {
				logError(e);
			}

			try {
				output.close();
			} catch (IOException e) {
				logError(e);
			}

		} catch (FileNotFoundException e) {
			logError(e);
		}

		return returnFlag;

	}

	/**
	 * 将输入流，输出为字节数组。
	 * 
	 * @注意：最后没有关闭inSream，需要函数外关闭。
	 * 
	 * @param inSream
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	protected byte[] readInputStreamToByte(InputStream inSream)
			throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;

		while ((len = inSream.read(buffer)) != -1 && !isStop) {
			outStream.write(buffer, 0, len);
		}

		byte[] data = outStream.toByteArray();
		outStream.close();

		return data;

	}

	public interface DownloadFileListener {
		void downloading(long count, long current, boolean refreshUI);
	}
}

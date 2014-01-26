package com.keanbin.framework.http.base;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.NameValuePair;

import com.keanbin.framework.http.Exception.BaseHttpConnectionException;
import com.keanbin.framework.http.Exception.BaseHttpParamErrorException;
import com.keanbin.framework.http.Exception.BaseHttpUserStopException;
import com.keanbin.framework.http.entity.DownloadFileInfo;
import com.keanbin.framework.http.entity.UploadFileInfo;

/**
 * 基本的网络请求类，使用HttpURLConnection方式。
 * 
 * @author keanbin
 * 
 */
public class BaseHttpURLConnection extends BaseHttpRequest {

	public BaseHttpURLConnection() {

	}

	@Override
	public byte[] getMethod(String urlStr, List<NameValuePair> nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException {

		if (urlStr == null) {
			throw new BaseHttpParamErrorException("httpGetRequest URL is null!");
		}

		HttpURLConnection conn = null;

		try {
			URL url = getUrl(urlStr, nameValuePairs);
			// 创建连接
			conn = (HttpURLConnection) url.openConnection();
			// 设置超时
			conn.setConnectTimeout(mRequestTimeOut);
			conn.setReadTimeout(mRequestTimeOut);
			conn.connect();

			if (conn.getResponseCode() != 200) {
				conn.disconnect();

				if (isStop) {
					throw new BaseHttpUserStopException(
							"User stop http request !");
				} else {
					logError("getMethodToByte ResponseCode = "
							+ conn.getResponseCode());
					throw new BaseHttpConnectionException(
							"httpGetRequest Fail !!");
				}
			}
			// 创建输入流
			InputStream is = conn.getInputStream();
			byte[] respon = null;

			try {
				respon = readInputStreamToByte(is);
			} catch (Exception e) {
				is.close();
				conn.disconnect();

				logError(e);
				if (isStop) {
					throw new BaseHttpUserStopException(
							"User stop http request !");
				} else {
					throw new BaseHttpConnectionException(
							"httpGetRequest Fail !!");
				}
			}

			is.close();
			conn.disconnect();

			if (isStop) {
				throw new BaseHttpUserStopException("User stop http request !");
			}

			return respon;

		} catch (UnsupportedEncodingException e) {

			if (conn != null) {
				conn.disconnect();
			}

			if (isStop) {
				throw new BaseHttpUserStopException("User stop http request !");
			} else {
				logError("getMethodToByte param value Encoding fail", e);
				throw new BaseHttpParamErrorException(
						"httpGetRequest param value Encoding fail!");
			}

		} catch (IOException e1) {
			if (conn != null) {
				conn.disconnect();
			}

			if (isStop) {
				throw new BaseHttpUserStopException("User stop http request !");
			} else {
				logError(e1);
				throw new BaseHttpConnectionException("httpGetRequest Fail !!");
			}
		}
	}

	/**
	 * 拼接网址和参数成 url地址，并获取 URL对象。用于Get方式。
	 * 
	 * @param urlStr
	 * @param nameValuePairs
	 * @return
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	private URL getUrl(String urlStr, List<NameValuePair> nameValuePairs)
			throws MalformedURLException, UnsupportedEncodingException {

		String params = joinParams(nameValuePairs);
		if (params != null) {
			urlStr += "?" + params;
		}

		return new URL(urlStr);
	}

	/**
	 * 拼接参数
	 * 
	 * @param nameValuePairs
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String joinParams(List<NameValuePair> nameValuePairs)
			throws UnsupportedEncodingException {
		if (nameValuePairs == null) {
			return null;
		}

		StringBuilder urlStringBuilder = new StringBuilder();

		for (int i = 0; i < nameValuePairs.size(); i++) {
			NameValuePair nameValuePair = nameValuePairs.get(i);
			urlStringBuilder.append(nameValuePair.getName() + "="
					+ URLEncoder.encode(nameValuePair.getValue(), "UTF-8")
					+ "&");
		}

		if (urlStringBuilder.length() > 0) {
			urlStringBuilder.deleteCharAt(urlStringBuilder.length() - 1);
			return urlStringBuilder.toString();
		} else {
			return null;
		}

	}

	@Override
	public byte[] postMethod(String urlStr, List<NameValuePair> nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException {

		if (urlStr == null) {
			throw new BaseHttpParamErrorException(
					"httpPostRequest URL is null!");
		}

		HttpURLConnection conn = null;

		try {
			// 封装参数
			String params = joinParams(nameValuePairs);
			byte[] data = null;
			if (params != null) {
				data = params.getBytes();
			}

			URL realUrl = new URL(urlStr);
			conn = (HttpURLConnection) realUrl.openConnection();

			// 设置超时
			conn.setConnectTimeout(mRequestTimeOut);
			conn.setReadTimeout(mRequestTimeOut);
			// 设置post请求
			conn.setDoOutput(true);// 发送POST请求必须设置允许输出
			conn.setUseCaches(false);// 不使用Cache
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Length",
					String.valueOf(data != null ? data.length : 0));
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			DataOutputStream outStream = new DataOutputStream(
					conn.getOutputStream());

			if (data != null) {
				outStream.write(data);
			}

			outStream.flush();

			if (conn.getResponseCode() != 200) {
				outStream.close();
				conn.disconnect();

				if (isStop) {
					throw new BaseHttpUserStopException(
							"User stop http request !");
				} else {
					logError("postMethodToByte ResponseCode = "
							+ conn.getResponseCode());
					throw new BaseHttpConnectionException(
							"httpPostRequest Fail !!");
				}
			}

			// 创建输入流，取出http请求结果
			InputStream is = conn.getInputStream();
			byte[] result = null;
			try {
				result = readInputStreamToByte(is);
			} catch (Exception e) {
				outStream.close();
				is.close();
				conn.disconnect();

				logError(e);
				if (isStop) {
					throw new BaseHttpUserStopException(
							"User stop http request !");
				} else {
					throw new BaseHttpConnectionException(
							"httpPostRequest Fail !!");
				}
			}

			outStream.close();
			is.close();
			conn.disconnect();

			if (isStop) {
				throw new BaseHttpUserStopException("User stop http request !");
			}

			return result;

		} catch (UnsupportedEncodingException e) {

			if (conn != null) {
				conn.disconnect();
			}

			if (isStop) {
				throw new BaseHttpUserStopException("User stop http request !");
			} else {
				logError("postMethodToByte param value Encoding fail", e);
				throw new BaseHttpParamErrorException(
						"httpPostRequest param value Encoding fail!");
			}

		} catch (IOException e1) {

			if (conn != null) {
				conn.disconnect();
			}

			if (isStop) {
				throw new BaseHttpUserStopException("User stop http request !");
			} else {
				logError(e1);
				throw new BaseHttpConnectionException("httpPostRequest Fail !!");
			}

		}
	}

	@Override
	public byte[] uploadFile(String uploadUrl,
			List<UploadFileInfo> uploadFileInfoList,
			List<NameValuePair> nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException {

		if (uploadUrl == null) {
			throw new BaseHttpParamErrorException("uploadUrl is null !");
		}

		if (uploadFileInfoList == null) {
			throw new BaseHttpParamErrorException(
					"uploadFileInfoList is null !");
		}

		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";

		HttpURLConnection conn = null;

		try {
			URL url = new URL(uploadUrl);
			conn = (HttpURLConnection) url.openConnection();

			// 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
			// 此方法用于在预先不知道内容长度时启用,没有进行内部缓冲的 HTTP 请求正文的流。
			conn.setChunkedStreamingMode(128 * 1024);// 128K
			// 允许输入输出流
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			// 设置超时
			conn.setConnectTimeout(mUploadFileTimeOut);
			conn.setReadTimeout(mUploadFileTimeOut);
			// 使用POST方法
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			DataOutputStream outStream = new DataOutputStream(
					conn.getOutputStream());

			// 上传参数
			if (nameValuePairs != null && !isStop) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < nameValuePairs.size(); i++) {

					if (isStop) {
						break;
					}

					NameValuePair param = nameValuePairs.get(i);

					sb.append(twoHyphens);
					sb.append(boundary);
					sb.append(end);
					sb.append("Content-Disposition: form-data; name=\""
							+ param.getName() + "\"" + end);
					sb.append("Content-Type: text/plain; charset=" + "UTF-8"
							+ end);
					sb.append("Content-Transfer-Encoding: 8bit" + end);
					sb.append(end);
					sb.append(param.getValue());
					sb.append(end);
				}
				outStream.write(sb.toString().getBytes());
			}

			// 上传文件
			if (uploadFileInfoList != null && !isStop) {
				for (int i = 0; i < uploadFileInfoList.size(); i++) {

					if (isStop) {
						break;
					}

					UploadFileInfo uploadFileInfo = uploadFileInfoList.get(i);
					if (uploadFileInfo.isError()) {
						continue;
					}

					String filePath = uploadFileInfo.getFilePath();
					String formName = uploadFileInfo.getFormName();

					// 上传文件信息
					outStream.writeBytes(twoHyphens + boundary + end);
					outStream
							.writeBytes("Content-Disposition: form-data; name=\""
									+ formName
									+ "\"; filename=\""
									+ filePath.substring(filePath
											.lastIndexOf("/") + 1) + "\"" + end);
					outStream.writeBytes(end);

					// 上传文件内容
					FileInputStream fis = new FileInputStream(filePath);
					byte[] buffer = new byte[8192]; // 8k
					int count = 0;
					// 读取文件
					while ((count = fis.read(buffer)) != -1 && !isStop) {
						outStream.write(buffer, 0, count);
					}
					fis.close();
					outStream.writeBytes(end);
				}
			}

			outStream.writeBytes(twoHyphens + boundary + twoHyphens + end);
			outStream.flush();

			if (conn.getResponseCode() != 200) {
				outStream.close();
				conn.disconnect();

				if (isStop) {
					throw new BaseHttpUserStopException(
							"User stop http request !");
				} else {
					logError("uploadFile ResponseCode = "
							+ conn.getResponseCode());
					throw new BaseHttpConnectionException("uploadFile Fail !!");
				}
			}

			// 创建输入流，取出http请求结果
			InputStream is = conn.getInputStream();
			byte[] result = null;
			if (!isStop) {
				try {
					result = readInputStreamToByte(is);
				} catch (Exception e) {
					outStream.close();
					is.close();
					conn.disconnect();

					logError(e);
					if (isStop) {
						throw new BaseHttpUserStopException(
								"User stop http request !");
					} else {
						throw new BaseHttpConnectionException(
								"uploadFile Fail !!");
					}
				}
			}

			outStream.close();
			is.close();
			conn.disconnect();

			if (isStop) {
				throw new BaseHttpUserStopException("User stop http request !");
			}

			return result;

		} catch (Exception e) {

			if (conn != null) {
				conn.disconnect();
			}

			if (isStop) {
				throw new BaseHttpUserStopException("User stop http request !");
			} else {
				logError(e);
				throw new BaseHttpConnectionException("uploadFile Fail !!");
			}
		}
	}

	@Override
	public String downLoadFile(String fileUrl, File file, boolean isResume,
			DownloadFileListener listener) throws BaseHttpParamErrorException,
			BaseHttpConnectionException, BaseHttpUserStopException {

		if (fileUrl == null) {
			throw new BaseHttpParamErrorException("fileUrl is null!");
		}

		if (file == null) {
			throw new BaseHttpParamErrorException("file is null!");
		}

		if (!file.exists() || !file.isFile()) {
			throw new BaseHttpParamErrorException("file is error!");
		}

		HttpURLConnection conn = null;

		try {
			URL url = new URL(fileUrl);
			// 创建连接
			conn = (HttpURLConnection) url.openConnection();
			// 设置超时
			conn.setConnectTimeout(mDownloadFileTimeOut);
			conn.setReadTimeout(mDownloadFileTimeOut);

			// 文件总大小
			long count = 0L;
			if (isResume) {
				long fileLen = file.length();

				// 从上次的下载信息文件中获取文件的总大小
				long fileTotalSize = DownloadFileInfo.getTotalSize(file
						.getAbsolutePath());

				if (fileLen > 0 && fileLen < fileTotalSize) {
					count += fileLen;

					log("downLoadFile  fileLen = " + fileLen);

					// 设置User-Agent
					conn.setRequestProperty("User-Agent", "NetFox");
					// 设置断点续传的开始位置
					conn.setRequestProperty("RANGE", "bytes=" + fileLen + "-");
				} else {
					isResume = false;
				}
			}

			count += conn.getContentLength();

			// 生成下载信息的信息文件
			DownloadFileInfo downloadFileInfo = new DownloadFileInfo();
			downloadFileInfo.setTotalSize(count);
			downloadFileInfo.toJsonFile(file.getAbsolutePath());

			// 创建输入流
			InputStream is = conn.getInputStream();

			if (readInputStreamToFile(file, is, isResume, count, listener)) {

				conn.disconnect();
				if (isStop) {
					throw new BaseHttpUserStopException(
							"User stop http request !");
				}

				return file.getAbsolutePath();
			} else {
				conn.disconnect();
				if (isStop) {
					throw new BaseHttpUserStopException(
							"User stop http request !");
				} else {
					throw new BaseHttpConnectionException(
							"downLoadFile Fail !!");
				}
			}
		} catch (IOException e) {

			if (conn != null) {
				conn.disconnect();
			}

			if (isStop) {
				throw new BaseHttpUserStopException("User stop http request !");
			} else {
				logError(e);
				throw new BaseHttpConnectionException("downLoadFile Fail !!");
			}
		}
	}
}

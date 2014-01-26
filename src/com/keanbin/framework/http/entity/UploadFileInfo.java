package com.keanbin.framework.http.entity;

import java.util.HashMap;

import com.keanbin.framework.base.BaseEntity;

/**
 * 上传的文件的信息
 * 
 * @author keanbin
 * 
 */
public class UploadFileInfo extends BaseEntity {

	private HashMap<String, String> fileInfo;

	public static final String KEY_FILE_PATH = "FilePath";
	public static final String KEY_FORM_NAME = "FormName";

	public static final String DEFAULT_FORM_NAME = "uploadfile";

	public UploadFileInfo(String filePath, String formName) {
		setFileInfo(filePath, formName);
	}

	public UploadFileInfo(String filePath) {
		this(filePath, null);
	}

	private void setFileInfo(String filePath, String formName) {
		if (filePath != null) {
			fileInfo = new HashMap<String, String>();
			fileInfo.put(KEY_FILE_PATH, filePath);
			if (formName != null) {
				fileInfo.put(KEY_FORM_NAME, formName);
			} else {
				fileInfo.put(KEY_FORM_NAME, DEFAULT_FORM_NAME);
			}
		}
	}

	@Override
	public boolean isError() {
		return fileInfo == null;
	}

	/**
	 * 获取文件的路径
	 * 
	 * @return
	 */
	public String getFilePath() {
		if (fileInfo == null) {
			return null;
		}

		return fileInfo.get(KEY_FILE_PATH);
	}

	/**
	 * 获取表单名称
	 * 
	 * @return
	 */
	public String getFormName() {
		if (fileInfo == null) {
			return null;
		}

		return fileInfo.get(KEY_FORM_NAME);
	}

}

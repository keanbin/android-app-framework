package com.keanbin.framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

public class FileUtil {

	private static final String TAG = FileUtil.class.getCanonicalName();

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 获取扩展的存储介质的路径，比如SD卡。
	 * 
	 * @return
	 */
	public static String getExtendedStorePath() {
		if (!Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return null;
		}

		File extendedStoreDir = Environment.getExternalStorageDirectory();

		if (extendedStoreDir == null) {
			return null;
		}

		return extendedStoreDir.toString() + "/";
	}

	/**
	 * 获取文件的目录路径（不包含文件名）
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getDirPath(String filePath) {
		if (StringUtil.isEmpty(filePath)) {
			return null;
		}

		if (filePath.lastIndexOf(File.separator) < 0) {
			return null;
		}

		return filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
	}

	/**
	 * 创建目录
	 * 
	 * @param dirPath
	 * @return
	 */
	public static File createDir(String dirPath) {
		if (StringUtil.isEmpty(dirPath)) {
			return null;
		}

		File dir = new File(dirPath);
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}

		if (!dir.exists() || !dir.isDirectory()) {
			return null;
		}

		return dir;
	}

	/**
	 * 创建文件，连目录也一起创建
	 * 
	 * @param filePath
	 * @return
	 */
	public static File createFile(String filePath) {
		if (StringUtil.isEmpty(filePath)) {
			return null;
		}

		File file = new File(filePath);

		if (file.exists() && file.isFile()) {
			return file;
		}

		String dirPath = getDirPath(filePath);
		if (!StringUtil.isEmpty(dirPath) && createDir(dirPath) == null) {
			return null;
		}

		try {
			file.createNewFile();
			return file;
		} catch (IOException e) {
			LogUtil.errorLog(TAG, e);
		}

		return null;

	}

	/**
	 * 删除文件，连目录也一起删除
	 * 
	 * @param filePath
	 *            文件的路径
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		if (StringUtil.isEmpty(filePath)) {
			return false;
		}

		File file = new File(filePath);
		return deleteFile(file);
	}

	/**
	 * 删除文件，连目录也一起删除
	 * 
	 * @param file
	 *            文件对象
	 * @return
	 */
	public static boolean deleteFile(File file) {
		if (!file.exists()) {
			return true;
		}

		if (file.isDirectory()) {
			File[] fileList = file.listFiles();

			for (File fileTmp : fileList) {
				deleteFile(fileTmp);
			}

			file.delete();
		} else {
			file.delete();
		}

		return true;
	}

	/**
	 * 写文件
	 * 
	 * @param file
	 *            文件对象
	 * @param content
	 *            写入的内容
	 * @param append
	 *            是否附加在文件末尾
	 * @return 成功返回true，失败返回false。
	 */
	public static boolean write(File file, byte[] content, boolean append) {
		if (file == null || content == null) {
			return false;
		}

		try {
			FileOutputStream fOut = new FileOutputStream(file, append);

			try {
				fOut.write(content);
				fOut.close();
				return true;
			} catch (IOException e) {
				LogUtil.errorLog(TAG, e);
			}

		} catch (FileNotFoundException e) {
			LogUtil.errorLog(TAG, e);
		}

		return false;
	}

	/**
	 * 写文件
	 * 
	 * @param file
	 *            文件对象
	 * @param content
	 *            写入的内容
	 * @return 成功返回true，失败返回false。
	 */
	public static boolean write(File file, byte[] content) {
		return write(file, content, false);
	}

	/**
	 * 写文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param content
	 *            写入的内容
	 * @param append
	 *            是否附加在文件末尾
	 * @return 成功返回true，失败返回false。
	 */
	public static boolean write(String filePath, byte[] content, boolean append) {
		if (filePath == null) {
			return false;
		}

		File file = createFile(filePath);

		if (file == null) {
			return false;
		}

		return write(file, content, append);
	}

	/**
	 * 写文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param content
	 *            写入的内容
	 * @return 成功返回true，失败返回false。
	 */
	public static boolean write(String filePath, byte[] content) {
		return write(filePath, content, false);
	}

	/**
	 * 读文件
	 * 
	 * @param inStream
	 * @return
	 */
	public static byte[] read(FileInputStream inStream) {
		if (inStream == null) {
			return null;
		}

		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}

			outStream.close();
			inStream.close();
			return outStream.toByteArray();
		} catch (IOException e) {
			LogUtil.errorLog(TAG, e);
		}
		return null;
	}

	/**
	 * 读文件
	 * 
	 * @param inStream
	 * @return
	 */
	public static String readToString(FileInputStream inStream) {
		byte[] data = read(inStream);
		if (data == null) {
			return null;
		}

		return (new String(data));
	}

	/**
	 * 读文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static byte[] read(String filePath) {

		if (StringUtil.isEmpty(filePath)) {
			return null;
		}

		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}

		try {
			FileInputStream inStream = new FileInputStream(file);
			return read(inStream);
		} catch (FileNotFoundException e) {
			LogUtil.errorLog(TAG, e);
		}

		return null;
	}

	/**
	 * 读文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readToString(String filePath) {
		byte[] data = read(filePath);
		if (data == null) {
			return null;
		}

		return (new String(data));
	}

	/**
	 * 写文件， 文件保存在Android系统中 /data/data/PACKAGE_NAME/files 目录下
	 * 
	 * @param context
	 * @param msg
	 */
	public static boolean write(Context context, String fileName, String content) {
		if (fileName == null || content == null) {
			return false;
		}

		try {
			FileOutputStream fos = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			fos.write(content.getBytes());

			fos.close();

			return true;
		} catch (Exception e) {
			LogUtil.errorLog(TAG, e);
		}

		return false;

	}

	/**
	 * 读文件， 文件保存在Android系统中 /data/data/PACKAGE_NAME/files 目录下
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String read(Context context, String fileName) {
		try {
			FileInputStream in = context.openFileInput(fileName);
			return readToString(in);
		} catch (Exception e) {
			LogUtil.errorLog(TAG, e);
		}

		return null;
	}

}

package com.keanbin.framework.utils;

/**
 * @ClassName CommonConfig
 * @Description 保存常量的类
 * @author kevin
 */
public class CommonConfig {

	// 调试开关
	public static final boolean DEBUG = true;

	// 域名
	public static final String URL_HEAD = "http://192.168.1.238/";
	//public static final String URL_HEAD = "http://192.168.1.104/";

	public static final String URL_VERSION_UPDATE = URL_HEAD + "versionUpdate.php"; // 版本更新地址

	// 文件保存的根目录
	public static final String ROOT_PATH = "/MakeGold/";
	public static final String APK_DOWNLOAD_PATH = ROOT_PATH + "/download/";

	// Intent 数据的key
	public static final String INTENT_DATA_HTTP_REQUEST_RESULT = "HttpRequestResult"; // Http请求结果
	// TODO Test
	public static final String INTENT_DATA_ITEM_INDEX = "ItemIndex"; // 链表项的索引
	public static final String INTENT_DATA_IMAGEINFO = "ImageInfo"; // ImageInfo实例

	// 广播的 ACTION
	// HTTP请求结果的广播
	public static final String BROADCAST_ACTION_HTTP_REQUEST_RESULT = "KEANBIN_HTTP_REQUEST_RESULT_BROADCAST";
}

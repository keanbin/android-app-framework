package com.keanbin.app.httpapi;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.keanbin.framework.http.HttpApi;
import com.keanbin.framework.http.entity.HttpCommonParams;
import com.keanbin.framework.utils.CommonConfig;
import com.keanbin.framework.utils.JsonUtil;
import com.keanbin.app.entity.ListTemplateInfo;
import com.keanbin.app.entity.PagingGetListHttpExtendParam;

/**
 * 获取收益详情
 * 
 * @author keanbin
 * 
 */
public class GetListTemplateHttpApi extends HttpApi {

	private static final String URL = CommonConfig.URL_HEAD + "list.php";// +"getIncomeDetail";

	/**
	 * Json格式的字段：数据
	 */
	public static final String JSON_FIELD_DATA = "data";

	/**
	 * Json格式的字段：列表
	 */
	private static final String JSON_FIELD_LIST = "list";

	public GetListTemplateHttpApi(Context context) {
		super(context);

	}

	public boolean sendHttpRequest(int pageNumber, int number) {

		PagingGetListHttpExtendParam extendParam = new PagingGetListHttpExtendParam();
		extendParam.setPageNumber(pageNumber);

		HttpCommonParams commonParams = new HttpCommonParams(URL);
		commonParams.setExtendParam(extendParam);

		return sendHttpRequest(commonParams, new BasicNameValuePair(
				"pageNumber", "" + pageNumber), new BasicNameValuePair(
				"number", "" + number));
	}

	public int jsonData(JSONObject dataObj,
			List<ListTemplateInfo> incomeRecordList) throws JSONException {
		if (dataObj == null || incomeRecordList == null) {
			return 0;
		}

		JSONArray list = JsonUtil.getJSONArray(dataObj, JSON_FIELD_LIST);
		return ListTemplateInfo.jsonParseList(list, incomeRecordList);
	}
}

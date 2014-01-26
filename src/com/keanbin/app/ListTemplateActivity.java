package com.keanbin.app;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.keanbin.framework.base.BaseActivity;
import com.keanbin.framework.http.HttpApi;
import com.keanbin.framework.http.entity.HttpResult;
import com.keanbin.framework.utils.JsonUtil;
import com.keanbin.framework.widget.PointRefresh;
import com.keanbin.framework.widget.PointRefresh.PointRefreshListener;
import com.keanbin.framework.widget.XListView;
import com.keanbin.framework.widget.XListView.IXListViewListener;
import com.keanbin.app.adapter.ListTemplateListAdapter;
import com.keanbin.app.db.ListTemplateDB;
import com.keanbin.app.entity.ListTemplateInfo;
import com.keanbin.app.entity.PagingGetListHttpExtendParam;
import com.keanbin.app.httpapi.GetListTemplateHttpApi;

/**
 * 收益详情
 * 
 * @author keanbin
 * 
 */
public class ListTemplateActivity extends BaseActivity {

	/**
	 * 一页的数据条数
	 */
	private static final int ONE_PAGE_NUMBER = 20;

	private XListView mIncomeRecordListXlv;
	private PointRefresh mPointRefresh;

	private Context mContext;
	private ListTemplateListAdapter mIncomeRecordListAdapter;
	private List<ListTemplateInfo> mIncomeRecordList;
	private GetListTemplateHttpApi mGetIncomeRecordHttpApi;
	private ListTemplateDB mIncomeRecordDB;
	private int mCurrentPageNum; // 当前页码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_template);

		if (!initData()) {
			finish();
		}

		initView();

		getDataFromDB();
		showContentArea();
		GetIncomeRecordList(1);
		showTitleRotateAnim();
		// 数据库没有数据
		if (mPointRefresh.getVisibility() == View.VISIBLE) {
			mPointRefresh.startRefresh();
		}
	}

	@Override
	protected void onHttpRequestReceiveResult(String flag, String result,
			HttpResult httpResult) {
		super.onHttpRequestReceiveResult(flag, result, httpResult);

		if (HttpApi.isSelfFlag(mGetIncomeRecordHttpApi, flag)) {

			PagingGetListHttpExtendParam extendParam = (PagingGetListHttpExtendParam) httpResult
					.getExtendParam();
			int pageNum = extendParam.getPageNumber();

			try {
				JSONObject resultObj = new JSONObject(result);
				JSONObject dataObj = JsonUtil.getJSONObject(resultObj,
						GetListTemplateHttpApi.JSON_FIELD_DATA);
				if (dataObj != null) {

					List<ListTemplateInfo> incomeRecordList = new ArrayList<ListTemplateInfo>();
					mGetIncomeRecordHttpApi.jsonData(dataObj, incomeRecordList);

					mCurrentPageNum = 1;
					if (pageNum == 1) {
						mIncomeRecordList.clear();
						mIncomeRecordDB.clear();
						mIncomeRecordDB.insert(incomeRecordList);
					}

					if (incomeRecordList != null && incomeRecordList.size() > 0) {
						mIncomeRecordList.addAll(incomeRecordList);
					}

					if (incomeRecordList.size() >= ONE_PAGE_NUMBER) {
						mIncomeRecordListXlv.setPullLoadEnable(true); // 设置页脚可见
					} else {
						mIncomeRecordListXlv.setPullLoadEnable(false); // 设置页脚不可见
					}
				}
			} catch (JSONException e) {
				logError(e);
			}

			finishLoading();
			// 根据情况显示内容区域
			showContentArea();
		}
	}

	@Override
	protected void onHttpRequestUserStop(String flag, HttpResult httpResult) {
		super.onHttpRequestUserStop(flag, httpResult);

		if (HttpApi.isSelfFlag(mGetIncomeRecordHttpApi, flag)) {
			finishLoading();
		}
	}

	@Override
	protected void onHttpRequestError(String flag, HttpResult httpResult) {
		super.onHttpRequestError(flag, httpResult);

		if (HttpApi.isSelfFlag(mGetIncomeRecordHttpApi, flag)) {
			showToast(mContext.getString(R.string.getDataFail));
			finishLoading();
		}
	}

	/**
	 * 初始化数据
	 * 
	 * @return
	 */
	private boolean initData() {

		mIncomeRecordList = new ArrayList<ListTemplateInfo>();
		mIncomeRecordListAdapter = new ListTemplateListAdapter(mContext,
				mIncomeRecordList);
		mCurrentPageNum = 1;
		mIncomeRecordDB = new ListTemplateDB(mContext);

		return true;
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		setTitleBarText("列表页面");
		showTitleBarLeftImage();

		mIncomeRecordListXlv = (XListView) findViewById(R.id.xlistView_activityIncomeDetail_incomeRecrodList);
		mIncomeRecordListXlv.setOnItemClickListener(mOnItemClickListener);
		mIncomeRecordListXlv.setXListViewListener(mIXListViewListener);
		mIncomeRecordListXlv.setAdapter(mIncomeRecordListAdapter);
		mIncomeRecordListXlv.setPullLoadEnable(false); // 设置页脚不可见

		mPointRefresh = (PointRefresh) findViewById(R.id.pointRefresh_activityIncomeDetail_refresh);
		mPointRefresh.setListener(mPointRefreshListener);
	}

	/**
	 * 根据情况显示内容区域
	 */
	private void showContentArea() {
		if (mIncomeRecordList.size() > 0) {
			mIncomeRecordListAdapter.notifyDataSetChanged();

			mIncomeRecordListXlv.setVisibility(View.VISIBLE);
			mPointRefresh.setVisibility(View.GONE);
		} else {
			mPointRefresh.setVisibility(View.VISIBLE);
			mIncomeRecordListXlv.setVisibility(View.GONE);
		}
	}

	/**
	 * 从数据库取数据
	 */
	private void getDataFromDB() {
		List<ListTemplateInfo> incomeRecordList = mIncomeRecordDB.query();
		if (incomeRecordList != null && incomeRecordList.size() > 0) {
			mIncomeRecordList.addAll(incomeRecordList);
		}
	}

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 存在页眉 position需要-1
			// int index = position - 1;

		}

	};

	private IXListViewListener mIXListViewListener = new IXListViewListener() {

		@Override
		public void onRefresh() {
			GetIncomeRecordList(1);
		}

		@Override
		public void onLoadMore() {
			GetIncomeRecordList(mCurrentPageNum + 1);
		}
	};

	private PointRefreshListener mPointRefreshListener = new PointRefreshListener() {

		@Override
		public void onClick() {
			GetIncomeRecordList(1);
		}
	};

	private void GetIncomeRecordList(int pageNum) {
		log("GetIncomeRecordList() : pageNum = " + pageNum);
		if (mGetIncomeRecordHttpApi != null) {
			mGetIncomeRecordHttpApi.stopHttpRequest();
		}

		mGetIncomeRecordHttpApi = new GetListTemplateHttpApi(mContext);
		mGetIncomeRecordHttpApi.sendHttpRequest(pageNum, ONE_PAGE_NUMBER);
	}

	/**
	 * 显示标题栏的旋转动画
	 */
	public void showTitleRotateAnim() {
		showTitleBarLeftRotateAnim(true);

	}

	/**
	 * 隐藏标题栏的旋转动画
	 */
	public void hideTitleRotateAnim() {
		showTitleBarLeftRotateAnim(false);
	}

	/**
	 * 加载完成
	 */
	public void finishLoading() {
		if (mIncomeRecordListXlv.getVisibility() == View.VISIBLE) {
			mIncomeRecordListXlv.stopRefreshAndLoadMore();
		}

		hideTitleRotateAnim();

		if (mPointRefresh.getVisibility() == View.VISIBLE) {
			mPointRefresh.stopRefresh();
		}
	}
}

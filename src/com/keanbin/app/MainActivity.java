package com.keanbin.app;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keanbin.app.widget.FourTabPage;
import com.keanbin.app.widget.OneTabPage;
import com.keanbin.app.widget.ThreeTabPage;
import com.keanbin.app.widget.TwoTabPage;
import com.keanbin.framework.base.BaseActivity;
import com.keanbin.framework.http.entity.HttpResult;
import com.keanbin.framework.widget.CustomViewPager;

public class MainActivity extends BaseActivity {

	private static final int TAB_ITEM_NUMBER = 4;

	private static final int ONE_PAGE_INDEX = 0; // 第一分页的索引
	private static final int TWO_PAGE_INDEX = 1; // 第二分页的索引
	private static final int THREE_PAGE_INDEX = 2; // 第三分页的索引
	private static final int FOUR_PAGE_INDEX = 3; // 第四分页的索引

	private CustomViewPager mViewPager; // 页面管理
	private ImageView[] mTabImage; // tab项的图标
	private TextView[] mTabText; // tab项的文本
	private LinearLayout mOneTabLl, mTwoTabLl, mThreeTabLl, mFourTabLl;// tab项的区域
	private OneTabPage mOneTabPage;// 第一分页
	private TwoTabPage mTwoTabPage; // 第二分页
	private ThreeTabPage mThreeTabPage;// 第三分页
	private FourTabPage mFourTabPage;// 第四分页
	private ArrayList<View> mPageList; // 页面列表
	private int currIndex; // 当前页卡编号

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setIsAutoAddTitle(false);
		setContentView(R.layout.activity_main);

		// 初始化数据
		if (!initData()) {
			finish();
			return;
		}

		// 初始化页面
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (mOneTabPage != null) {
			mOneTabPage.initContentData();
			mOneTabPage.onResume();
		}

		if (mTwoTabPage != null) {
			mTwoTabPage.onResume();
		}

		if (mThreeTabPage != null) {
			mThreeTabPage.onResume();
		}

		if (mFourTabPage != null) {
			mFourTabPage.onResume();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (mTwoTabPage != null) {
			mTwoTabPage.onDestroy();
		}

		if (mThreeTabPage != null) {
			mThreeTabPage.onDestroy();
		}

		if (mOneTabPage != null) {
			mOneTabPage.onDestroy();
		}

		if (mFourTabPage != null) {
			mFourTabPage.onDestroy();
		}

	}

	@Override
	protected void onHttpRequestStart(String flag, HttpResult httpResult) {
		super.onHttpRequestStart(flag, httpResult);

	}

	@Override
	protected void onHttpRequestProcess(String flag, long count, long current,
			HttpResult httpResult) {
		super.onHttpRequestProcess(flag, count, current, httpResult);

	}

	@Override
	protected void onHttpRequestReceiveResult(String flag, String result,
			HttpResult httpResult) {
		super.onHttpRequestReceiveResult(flag, result, httpResult);

	}

	@Override
	protected void onHttpRequestUserStop(String flag, HttpResult httpResult) {
		super.onHttpRequestUserStop(flag, httpResult);

	}

	@Override
	protected void onHttpRequestError(String flag, HttpResult httpResult) {
		super.onHttpRequestError(flag, httpResult);

	}

	/**
	 * @Description 初始化数据
	 * @return
	 */
	private boolean initData() {
		mTabImage = new ImageView[TAB_ITEM_NUMBER];
		mTabText = new TextView[TAB_ITEM_NUMBER];
		currIndex = -1;

		return true;
	}

	/**
	 * @Description 初始化页面
	 * @return
	 */
	private void initView() {
		mViewPager = (CustomViewPager) findViewById(R.id.vp_activityMain_viewPage);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mViewPager.setIsSlideSkip(false);

		mTabImage[ONE_PAGE_INDEX] = (ImageView) findViewById(R.id.iv_activityMain_oneTab);
		mTabImage[TWO_PAGE_INDEX] = (ImageView) findViewById(R.id.iv_activityMain_twoTab);
		mTabImage[THREE_PAGE_INDEX] = (ImageView) findViewById(R.id.iv_activityMain_threeTab);
		mTabImage[FOUR_PAGE_INDEX] = (ImageView) findViewById(R.id.iv_activityMain_fourTab);

		mTabText[ONE_PAGE_INDEX] = (TextView) findViewById(R.id.tv_activityMain_oneTab);
		mTabText[TWO_PAGE_INDEX] = (TextView) findViewById(R.id.tv_activityMain_twoTab);
		mTabText[THREE_PAGE_INDEX] = (TextView) findViewById(R.id.tv_activityMain_threeTab);
		mTabText[FOUR_PAGE_INDEX] = (TextView) findViewById(R.id.tv_activityMain_fourTab);

		mOneTabLl = (LinearLayout) findViewById(R.id.ll_activityMain_oneTab);
		mTwoTabLl = (LinearLayout) findViewById(R.id.ll_activityMain_twoTab);
		mThreeTabLl = (LinearLayout) findViewById(R.id.ll_activityMain_threeTab);
		mFourTabLl = (LinearLayout) findViewById(R.id.ll_activityMain_fourTab);

		mOneTabLl.setOnClickListener(mViewOnClickListener);
		mTwoTabLl.setOnClickListener(mViewOnClickListener);
		mThreeTabLl.setOnClickListener(mViewOnClickListener);
		mFourTabLl.setOnClickListener(mViewOnClickListener);

		// 填充分页列表
		mOneTabPage = new OneTabPage(mContext);
		mTwoTabPage = new TwoTabPage(mContext);
		mThreeTabPage = new ThreeTabPage(mContext);
		mFourTabPage = new FourTabPage(mContext);

		mPageList = new ArrayList<View>();
		mPageList.add(mOneTabPage);
		mPageList.add(mTwoTabPage);
		mPageList.add(mThreeTabPage);
		mPageList.add(mFourTabPage);

		mViewPager.setAdapter(mPagerAdapter);
		setCurrentItem(ONE_PAGE_INDEX, false);
	}

	/* 填充ViewPager的数据适配器 */
	private PagerAdapter mPagerAdapter = new PagerAdapter() {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return mPageList.size();
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(mPageList.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(mPageList.get(position));
			return mPageList.get(position);
		}
	};

	/* view 点击监听器 */
	private OnClickListener mViewOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ll_activityMain_oneTab:
				setCurrentItem(ONE_PAGE_INDEX, false);
				break;

			case R.id.ll_activityMain_twoTab:
				setCurrentItem(TWO_PAGE_INDEX, false);
				break;

			case R.id.ll_activityMain_threeTab:
				setCurrentItem(THREE_PAGE_INDEX, false);
				break;

			case R.id.ll_activityMain_fourTab:
				setCurrentItem(FOUR_PAGE_INDEX, false);
				break;

			default:
				break;
			}

		}
	};

	/**
	 * @ClassName MyOnPageChangeListener
	 * @Description 页面切换监听器
	 * @author kevin
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {

			switch (arg0) {
			case ONE_PAGE_INDEX:
				if (mOneTabPage != null) {
					mOneTabPage.initContentData();
					mOneTabPage.onResume();
				}
				break;

			case TWO_PAGE_INDEX:
				if (mTwoTabPage != null) {
					mTwoTabPage.initContentData();
					mTwoTabPage.onResume();
				}
				break;

			case THREE_PAGE_INDEX:
				if (mThreeTabPage != null) {
					mThreeTabPage.initContentData();
					mThreeTabPage.onResume();
				}
				break;

			case FOUR_PAGE_INDEX:
				if (mFourTabPage != null) {
					mFourTabPage.initContentData();
					mFourTabPage.onResume();
				}
				break;
			}

			changeTabSelectedDisp(arg0);
			currIndex = arg0;
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	/**
	 * 修改 Tab选中显示
	 * 
	 * @param selectIndex
	 */
	public void changeTabSelectedDisp(int selectIndex) {

		if (selectIndex < 0 || selectIndex >= TAB_ITEM_NUMBER
				|| currIndex == selectIndex) {
			return;
		}

		// 设置Tab文本
		if (currIndex >= 0 && currIndex < TAB_ITEM_NUMBER) {
			mTabText[currIndex].setTextColor(mContext.getResources().getColor(
					R.color.tabTextColor_normal));
		}
		mTabText[selectIndex].setTextColor(mContext.getResources().getColor(
				R.color.tabTextColor_press));

		// 设置Tab图标
		switch (currIndex) {
		case ONE_PAGE_INDEX:
			mTabImage[ONE_PAGE_INDEX]
					.setImageResource(R.drawable.tab_one_normal);
			break;

		case TWO_PAGE_INDEX:
			mTabImage[TWO_PAGE_INDEX]
					.setImageResource(R.drawable.tab_two_normal);
			break;

		case THREE_PAGE_INDEX:
			mTabImage[THREE_PAGE_INDEX]
					.setImageResource(R.drawable.tab_three_normal);
			break;

		case FOUR_PAGE_INDEX:
			mTabImage[FOUR_PAGE_INDEX]
					.setImageResource(R.drawable.tab_four_normal);
			break;
		}

		switch (selectIndex) {
		case ONE_PAGE_INDEX:
			mTabImage[ONE_PAGE_INDEX]
					.setImageResource(R.drawable.tab_one_pressed);
			break;

		case TWO_PAGE_INDEX:
			mTabImage[TWO_PAGE_INDEX]
					.setImageResource(R.drawable.tab_two_pressed);
			break;

		case THREE_PAGE_INDEX:
			mTabImage[THREE_PAGE_INDEX]
					.setImageResource(R.drawable.tab_three_pressed);
			break;

		case FOUR_PAGE_INDEX:
			mTabImage[FOUR_PAGE_INDEX]
					.setImageResource(R.drawable.tab_four_pressed);
			break;
		}
	}

	/**
	 * 设置 ViewPage 选中项
	 * 
	 * @param position
	 * @param flag
	 */
	public void setCurrentItem(int position, boolean flag) {
		if (mViewPager.getCurrentItem() == position) {
			changeTabSelectedDisp(position);
			currIndex = position;
		} else {
			mViewPager.setCurrentItem(position, flag);
		}
	}
}

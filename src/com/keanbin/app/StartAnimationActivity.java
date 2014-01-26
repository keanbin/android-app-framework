package com.keanbin.app;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.keanbin.framework.base.BaseActivity;

public class StartAnimationActivity extends BaseActivity {

	private ViewPager mViewPager;
	private ImageView mImageView01;
	private ImageView mImageView02;
	private ImageView mImageView03;
	private ImageView mImageView04;
	private ImageView mImageView05;
	private ImageView mImageView06;

	private ArrayList<View> mPageList; // 页面列表

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setIsAutoAddTitle(false);
		setContentView(R.layout.activity_start_animation);

		initView();
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.vp_activityStartAnimation_viewPage);
		mViewPager.setOnPageChangeListener(mOnPageChangeListener);

		mImageView01 = (ImageView) findViewById(R.id.iv_activityStartAnimation_01);
		mImageView02 = (ImageView) findViewById(R.id.iv_activityStartAnimation_02);
		mImageView03 = (ImageView) findViewById(R.id.iv_activityStartAnimation_03);
		mImageView04 = (ImageView) findViewById(R.id.iv_activityStartAnimation_04);
		mImageView05 = (ImageView) findViewById(R.id.iv_activityStartAnimation_05);
		mImageView06 = (ImageView) findViewById(R.id.iv_activityStartAnimation_06);

		LayoutInflater mLi = LayoutInflater.from(this);
		View view01 = mLi.inflate(R.layout.start_anim_page01, null);
		View view02 = mLi.inflate(R.layout.start_anim_page02, null);
		View view03 = mLi.inflate(R.layout.start_anim_page03, null);
		View view04 = mLi.inflate(R.layout.start_anim_page04, null);
		View view05 = mLi.inflate(R.layout.start_anim_page05, null);
		View view06 = mLi.inflate(R.layout.start_anim_page06, null);

		mPageList = new ArrayList<View>();
		mPageList.add(view01);
		mPageList.add(view02);
		mPageList.add(view03);
		mPageList.add(view04);
		mPageList.add(view05);
		mPageList.add(view06);

		mViewPager.setAdapter(mPagerAdapter);
	}

	PagerAdapter mPagerAdapter = new PagerAdapter() {

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

	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				mImageView01.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index_now));
				mImageView02.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index));
				break;
			case 1:
				mImageView02.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index_now));
				mImageView01.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index));
				mImageView03.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index));
				break;
			case 2:
				mImageView03.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index_now));
				mImageView02.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index));
				mImageView04.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index));
				break;
			case 3:
				mImageView04.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index_now));
				mImageView05.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index));
				mImageView03.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index));
				break;
			case 4:
				mImageView05.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index_now));
				mImageView04.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index));
				mImageView06.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index));
				break;
			case 5:
				mImageView06.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index_now));
				mImageView05.setImageDrawable(getResources().getDrawable(
						R.drawable.icon_page_index));
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	public void startbutton(View v) {
		startActivity(FirstActivity.class);
		this.finish();
	}
}

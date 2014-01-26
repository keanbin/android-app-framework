package com.keanbin.app;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.keanbin.framework.base.BaseActivity;

public class FirstActivity extends BaseActivity {

	private Handler mHandler = new Handler();
	private boolean mIsDestroy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setIsAutoAddTitle(false);
		setContentView(R.layout.activity_first);

		mIsDestroy = false;
	}

	@Override
	protected void onResume() {
		super.onResume();

		mHandler.postDelayed(mRunnable, 200);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mHandler.removeCallbacks(mRunnable);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			mIsDestroy = true;
			mHandler.removeCallbacks(mRunnable);
			finish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {

			if (!mIsDestroy) {
				startActivity(MainActivity.class);
				finish();
			}
		}
	};

}

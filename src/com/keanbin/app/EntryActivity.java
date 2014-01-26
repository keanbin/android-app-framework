package com.keanbin.app;

import android.app.Activity;
import android.os.Bundle;

import com.keanbin.app.SharedPreferences.SundriesInfoSharePre;
import com.keanbin.framework.base.BaseActivity;

/**
 * 入口界面
 * 
 * @author keanbin
 * 
 */
public class EntryActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setIsAutoAddTitle(false);

		//startActivity(TestActivity.class);
		toFirstActivity();
	}

	@Override
	public void startActivity(Class<? extends Activity> toActivity) {
		super.startActivity(toActivity);
		finish();
	}

	private void toFirstActivity() {
		SundriesInfoSharePre sundriesInfoSharePre = SundriesInfoSharePre
				.getInstance(mContext);

		if (sundriesInfoSharePre.isAppFirstStart()) {
			sundriesInfoSharePre.setLastOpenVersionCode();

			startActivity(StartAnimationActivity.class);
		} else {
			startActivity(FirstActivity.class);
		}
	}

}

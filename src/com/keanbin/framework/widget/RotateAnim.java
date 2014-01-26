package com.keanbin.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keanbin.app.R;

/**
 * 刷新动画
 * 
 * @author keanbin
 * 
 */
public class RotateAnim extends RelativeLayout {

	private ImageView mRefreshIv;
	private TextView mTextTv;

	private Animation mAnimation;
	private Context mContext;

	public RotateAnim(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initView();
	}

	public RotateAnim(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	public RotateAnim(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	/**
	 * @Description 初始化布局
	 * @return
	 */
	public void initView() {
		View v = LayoutInflater.from(mContext).inflate(
				R.layout.widget_rotate_anim, this, true);

		mRefreshIv = (ImageView) v
				.findViewById(R.id.iv_widgetRotateAnim_refresh);
		mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
		// mRefreshIv.startAnimation(mAnimation);

		mTextTv = (TextView) v.findViewById(R.id.tv_widgetRotateAnim_text);

	}

	/**
	 * 设置旋转图片
	 * 
	 * @param resId
	 */
	public void setRotateImage(int resId) {
		if (mRefreshIv == null) {
			return;
		}

		mRefreshIv.setImageResource(resId);
	}

	/**
	 * 设置文本
	 * 
	 * @param text
	 */
	public void setText(String text) {
		if (mTextTv == null) {
			return;
		}

		mTextTv.setText(text);
	}

	/**
	 * 设置文本大小
	 * 
	 * @param size
	 */
	public void setTextSize(float size) {
		if (mTextTv == null) {
			return;
		}

		mTextTv.setTextSize(size);
	}

	/**
	 * 设置文本颜色
	 * 
	 * @param color
	 */
	public void setTextColor(int color) {
		if (mTextTv == null) {
			return;
		}

		mTextTv.setTextColor(color);
	}

	/**
	 * 设置是否显示文本，默认显示。
	 * 
	 * @param b
	 */
	public void showText(boolean b) {
		if (mTextTv == null) {
			return;
		}

		if (b) {
			mTextTv.setVisibility(View.VISIBLE);
		} else {
			mTextTv.setVisibility(View.GONE);
		}
	}

	/**
	 * 启动动画。这个函数是为了解决ViewPage翻页时，动画会异常停止的问题，当异常停止之后，页面刷新的时候，主动调用该函数，启动动画。
	 */
	public void startAnim() {
		// if(!mAnimation.hasStarted()){
		mRefreshIv.startAnimation(mAnimation);
		// }
	}

}

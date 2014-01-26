package com.keanbin.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keanbin.app.R;

/**
 * 标题栏
 * 
 * @author keanbin
 * 
 */
public class TitleBar extends RelativeLayout {

	private RelativeLayout mTitleBarRl;
	private ImageButton mLeftImgBtn; // 左图片按钮
	private Button mLeftBtn; // 左按钮
	private RotateAnim mLeftRotateAnim; // 左边旋转动画
	private TextView mTitleTv; // 标题
	private Button mRightBtn; // 右按钮
	private ImageButton mRightImgBtn; // 右图片按钮
	private RotateAnim mRightRotateAnim; // 右边旋转动画

	private Context mContext;
	private LeftImgBtnOnClickListener mLeftImgBtnOnClickListener; // 左图片按钮点击监听器
	private LeftBtnOnClickListener mLeftBtnOnClickListener; // 左按钮点击监听器
	private RightImgBtnOnClickListener mRightImgBtnOnClickListener; // 右图片按钮点击监听器
	private RightBtnOnClickListener mRightBtnOnClickListener; // 右按钮点击监听器

	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initView(context);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		initView(context);
	}

	public TitleBar(Context context) {
		super(context);

		initView(context);
	}

	/**
	 * 初始化布局
	 * 
	 * @return
	 */
	public void initView(Context context) {
		mContext = context;

		View v = LayoutInflater.from(mContext).inflate(
				R.layout.widget_title_bar, this, true);

		mTitleBarRl = (RelativeLayout) v
				.findViewById(R.id.rl_titleBarWidget_titleBar);
		mLeftImgBtn = (ImageButton) v
				.findViewById(R.id.imgBtn_titleBarWidget_leftImgBtn);
		mLeftBtn = (Button) v.findViewById(R.id.btn_titleBarWidget_leftBtn);
		mTitleTv = (TextView) v.findViewById(R.id.tv_titleBarWidget_titelText);
		mRightBtn = (Button) v.findViewById(R.id.btn_titleBarWidget_rightBtn);
		mRightImgBtn = (ImageButton) v
				.findViewById(R.id.imgBtn_titleBarWidget_rightImgBtn);

		mLeftImgBtn.setOnClickListener(mOnClickListener);
		mLeftBtn.setOnClickListener(mOnClickListener);
		mRightBtn.setOnClickListener(mOnClickListener);
		mRightImgBtn.setOnClickListener(mOnClickListener);

		mLeftRotateAnim = (RotateAnim) v
				.findViewById(R.id.rotateAnim_titleBarWidget_leftRotate);
		mRightRotateAnim = (RotateAnim) v
				.findViewById(R.id.rotateAnim_titleBarWidget_rightRotate);

	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imgBtn_titleBarWidget_leftImgBtn: {
				if (mLeftImgBtnOnClickListener != null) {
					mLeftImgBtnOnClickListener.onClick();
				}
			}
				break;

			case R.id.btn_titleBarWidget_leftBtn: {
				if (mLeftBtnOnClickListener != null) {
					mLeftBtnOnClickListener.onClick();
				}
			}
				break;

			case R.id.imgBtn_titleBarWidget_rightImgBtn: {
				if (mRightImgBtnOnClickListener != null) {
					mRightImgBtnOnClickListener.onClick();
				}
			}
				break;

			case R.id.btn_titleBarWidget_rightBtn: {
				if (mRightBtnOnClickListener != null) {
					mRightBtnOnClickListener.onClick();
				}
			}
				break;

			default:
				break;
			}

		}
	};

	/**
	 * 重置
	 */
	public void reset() {

		if (mLeftImgBtn != null) {
			mLeftImgBtn.setVisibility(View.GONE);
		}

		if (mLeftBtn != null) {
			mLeftBtn.setVisibility(View.GONE);
		}

		if (mRightImgBtn != null) {
			mRightImgBtn.setVisibility(View.GONE);
		}

		if (mRightBtn != null) {
			mRightBtn.setVisibility(View.GONE);
		}

		if (mLeftRotateAnim != null) {
			mLeftRotateAnim.setVisibility(View.GONE);
		}

		if (mRightRotateAnim != null) {
			mRightRotateAnim.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置标题栏背景
	 * 
	 * @param bgResid
	 */
	public void setTitleBarBg(int bgResid) {
		if (mTitleBarRl == null) {
			return;
		}

		mTitleBarRl.setBackgroundResource(bgResid);
	}

	/**
	 * 设置标题文本是否可见。如果可见，并且设置标题字符串。
	 * 
	 * @param b
	 * @param text
	 *            标题字符串
	 * @param bgResid
	 *            背景图片的资源ID
	 * @return
	 */
	public void setTitleText(String text) {
		if (mTitleTv == null) {
			return;
		}

		mTitleTv.setText(text);
	}

	/**
	 * 显示标题文本，默认显示。
	 * 
	 * @param b
	 */
	public void showTitleText(boolean b) {
		if (mTitleTv == null) {
			return;
		}

		if (b) {
			mTitleTv.setVisibility(View.VISIBLE);
		} else {
			mTitleTv.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置左边图片按钮是否可见，默认不可见。如果设置可见，左边的按钮就不可见。
	 * 
	 * @param b
	 * @param imgResid
	 *            图片资源ID
	 * @param l
	 *            点击监听器
	 * @return
	 */
	public void setLeftImgBtn(int imgResid, LeftImgBtnOnClickListener l) {
		if (mLeftImgBtn == null) {
			return;
		}

		mLeftImgBtn.setImageResource(imgResid);
		mLeftImgBtnOnClickListener = l;
	}

	/**
	 * 显示左边图片按钮，默认不显示。
	 * 
	 * @param b
	 */
	public void showLeftImgBtn(boolean b) {
		if (mLeftImgBtn == null) {
			return;
		}

		if (b) {
			mLeftImgBtn.setVisibility(View.VISIBLE);
			mLeftBtn.setVisibility(View.GONE);
		} else {
			mLeftImgBtn.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置左边按钮是否可见，默认不可见。如果设置可见，左边的图片按钮就不可见，并且设置按钮字符串。
	 * 
	 * @param b
	 * @param text
	 *            按钮文本
	 * @param bgResid
	 *            背景图片的资源ID
	 * @param l
	 *            点击监听器
	 * @return
	 */
	public void setLeftBtn(String text, int bgResid, LeftBtnOnClickListener l) {
		if (mLeftBtn == null) {
			return;
		}

		mLeftBtn.setText(text);
		mLeftBtn.setBackgroundResource(bgResid);
		mLeftBtnOnClickListener = l;
	}

	/**
	 * 显示左边按钮，默认不显示。
	 * 
	 * @param b
	 */
	public void showLeftBtn(boolean b) {
		if (mLeftBtn == null) {
			return;
		}

		if (b) {
			mLeftBtn.setVisibility(View.VISIBLE);
			mLeftImgBtn.setVisibility(View.GONE);
		} else {
			mLeftBtn.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置右边图片按钮是否可见，默认不可见。如果设置可见，右边的按钮就不可见。
	 * 
	 * @param b
	 * @param imgResid
	 *            图片资源ID
	 * @param l
	 *            点击监听器
	 * @return
	 */
	public void setRightImgBtn(int imgResid, RightImgBtnOnClickListener l) {
		if (mRightImgBtn == null) {
			return;
		}

		mRightImgBtn.setImageResource(imgResid);
		mRightImgBtnOnClickListener = l;
	}

	/**
	 * 显示右边图片按钮，默认不显示。
	 * 
	 * @param b
	 */
	public void showRightImgBtn(boolean b) {
		if (mRightImgBtn == null) {
			return;
		}

		if (b) {
			mRightImgBtn.setVisibility(View.VISIBLE);
			mRightBtn.setVisibility(View.GONE);
		} else {
			mRightImgBtn.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置右边按钮是否可见，默认不可见。如果设置可见，右边的图片按钮就不可见，并且设置按钮字符串。
	 * 
	 * @param b
	 * @param text
	 *            按钮文本
	 * @param bgResid
	 *            背景图片的资源ID
	 * @param l
	 *            点击监听器
	 * @return
	 */
	public void setRightBtn(String text, int bgResid, RightBtnOnClickListener l) {
		if (mRightBtn == null) {
			return;
		}

		mRightBtn.setText(text);
		mRightBtn.setBackgroundResource(bgResid);
		mRightBtnOnClickListener = l;
	}

	/**
	 * 显示右边按钮，默认不显示。
	 * 
	 * @param b
	 */
	public void showRightBtn(boolean b) {
		if (mRightBtn == null) {
			return;
		}

		if (b) {
			mRightBtn.setVisibility(View.VISIBLE);
			mRightImgBtn.setVisibility(View.GONE);
		} else {
			mRightBtn.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置左边旋转动画的文本
	 * 
	 * @param text
	 */
	public void setLeftRotateAnimText(String text) {
		if (mLeftRotateAnim == null) {
			return;
		}

		mLeftRotateAnim.setText(text);
	}

	/**
	 * 显示左边旋转动画，默认不显示。
	 * 
	 * @param b
	 */
	public void showLeftRotateAnim(boolean b) {
		if (mLeftRotateAnim == null) {
			return;
		}

		if (b) {
			mLeftRotateAnim.setVisibility(View.VISIBLE);
			mLeftRotateAnim.startAnim();
		} else {
			mLeftRotateAnim.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置右边旋转动画的文本
	 * 
	 * @param text
	 */
	public void setRightRotateAnimText(String text) {
		if (mRightRotateAnim == null) {
			return;
		}

		mRightRotateAnim.setText(text);
	}

	/**
	 * 显示右边旋转动画，默认不显示。
	 * 
	 * @param b
	 */
	public void showRightRotateAnim(boolean b) {
		if (mRightRotateAnim == null) {
			return;
		}

		if (b) {
			mRightRotateAnim.setVisibility(View.VISIBLE);
			mRightRotateAnim.startAnim();
		} else {
			mRightRotateAnim.setVisibility(View.GONE);
		}
	}

	/**
	 * 左图片按钮点击监听器
	 * 
	 * @ClassName LeftImgBtnOnClickListener
	 * @author kevin
	 */
	public interface LeftImgBtnOnClickListener {

		public void onClick();
	}

	/**
	 * 左按钮点击监听器
	 * 
	 * @ClassName LeftBtnOnClickListener
	 * @author kevin
	 */
	public interface LeftBtnOnClickListener {

		public void onClick();
	}

	/**
	 * 右图片按钮点击监听器
	 * 
	 * @ClassName RightImgBtnOnClickListener
	 * @author kevin
	 */
	public interface RightImgBtnOnClickListener {

		public void onClick();
	}

	/**
	 * 右按钮点击监听器
	 * 
	 * @ClassName RightBtnOnClickListener
	 * @author kevin
	 */
	public interface RightBtnOnClickListener {

		public void onClick();
	}

}

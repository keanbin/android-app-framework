package com.keanbin.framework.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.keanbin.framework.adapter.SingleChoiceAdapter;

/**
 * 单项选择列表对话框
 * 
 * @author keanbin
 * 
 */
public class SingleChoiceListDialog extends ListDialog {

	private SingleChoiceListDialogListener mListener;
	private SingleChoiceAdapter mSingleChoiceAdapter;

	public SingleChoiceListDialog(Context context, CharSequence[] items,
			int select, SingleChoiceListDialogListener l) {
		super(context);

		initDialog(items, select, l);
	}

	public SingleChoiceListDialog(Context context, int style, int select,
			CharSequence[] items, SingleChoiceListDialogListener l) {
		super(context, style);

		initDialog(items, select, l);
	}

	private void initDialog(CharSequence[] items, int select,
			SingleChoiceListDialogListener l) {
		mListener = l;

		mSingleChoiceAdapter = new SingleChoiceAdapter(mContext, items, select);

		setListAdapter(mSingleChoiceAdapter);
		setListOnItemClickListener(mOnItemClickListener);
	}

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			mSingleChoiceAdapter.setSelect(position);

			if (mListener != null) {
				mListener.onClick(SingleChoiceListDialog.this, position);
			}
		}
	};

	/**
	 * 设置未选中时的图标
	 * 
	 * @param drawable
	 */
	public void setNormalDrawable(Drawable drawable) {
		mSingleChoiceAdapter.setNormalDrawable(drawable);
	}

	/**
	 * 设置未选中时的图标
	 * 
	 * @param resId
	 */
	public void setNormalDrawable(int resId) {
		mSingleChoiceAdapter.setNormalDrawable(resId);
	}

	/**
	 * 设置选中时的图标
	 * 
	 * @param drawable
	 */
	public void setSelectedDrawable(Drawable drawable) {
		mSingleChoiceAdapter.setSelectedDrawable(drawable);
	}

	/**
	 * 设置选中时的图标
	 * 
	 * @param resId
	 */
	public void setSelectedDrawable(int resId) {
		mSingleChoiceAdapter.setSelectedDrawable(resId);
	}

	public interface SingleChoiceListDialogListener {
		void onClick(DialogInterface dialog, int posit);
	}

}

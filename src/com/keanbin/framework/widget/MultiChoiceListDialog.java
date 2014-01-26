package com.keanbin.framework.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.keanbin.framework.adapter.MultiChoiceAdapter;

/**
 * 复选框列表对话框
 * 
 * @author keanbin
 * 
 */
public class MultiChoiceListDialog extends ListDialog {

	private MultiChoiceListDialogListener mListener;
	private MultiChoiceAdapter mMultiChoiceAdapter;

	public MultiChoiceListDialog(Context context, CharSequence[] items,
			boolean[] checkedItems, MultiChoiceListDialogListener l) {
		super(context);

		initDialog(items, checkedItems, l);
	}

	public MultiChoiceListDialog(Context context, int style,
			boolean[] checkedItems, CharSequence[] items,
			MultiChoiceListDialogListener l) {
		super(context, style);

		initDialog(items, checkedItems, l);
	}

	private void initDialog(CharSequence[] items, boolean[] checkedItems,
			MultiChoiceListDialogListener l) {
		mListener = l;

		mMultiChoiceAdapter = new MultiChoiceAdapter(mContext, items,
				checkedItems);

		setListAdapter(mMultiChoiceAdapter);
		setListOnItemClickListener(mOnItemClickListener);
	}

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			boolean isChecked = mMultiChoiceAdapter.setCheck(position);

			if (mListener != null) {
				mListener.onClick(MultiChoiceListDialog.this, position,
						isChecked);
			}
		}
	};

	/**
	 * 设置未选中时的图标
	 * 
	 * @param drawable
	 */
	public void setNormalDrawable(Drawable drawable) {
		mMultiChoiceAdapter.setNormalDrawable(drawable);
	}

	/**
	 * 设置未选中时的图标
	 * 
	 * @param resId
	 */
	public void setNormalDrawable(int resId) {
		mMultiChoiceAdapter.setNormalDrawable(resId);
	}

	/**
	 * 设置选中时的图标
	 * 
	 * @param drawable
	 */
	public void setSelectedDrawable(Drawable drawable) {
		mMultiChoiceAdapter.setSelectedDrawable(drawable);
	}

	/**
	 * 设置选中时的图标
	 * 
	 * @param resId
	 */
	public void setSelectedDrawable(int resId) {
		mMultiChoiceAdapter.setSelectedDrawable(resId);
	}

	public interface MultiChoiceListDialogListener {
		void onClick(DialogInterface dialog, int posit, boolean isChecked);
	}

}

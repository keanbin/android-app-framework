package com.keanbin.framework.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.keanbin.app.R;
import com.keanbin.framework.adapter.TextAdapter;
import com.keanbin.framework.base.BaseDialog;

/**
 * 列表对话框
 * 
 * @author keanbin
 * 
 */
public class ListDialog extends BaseDialog {

	private ListView mListView;

	private ListDialogOnClickListener mListDialogOnClickListener;
	private ListAdapter mListAdapter;

	public ListDialog(Context context, CharSequence[] items,
			ListDialogOnClickListener l) {
		this(context, R.style.defaultDialogTheme, items, l);
	}

	public ListDialog(Context context, int style, CharSequence[] items,
			ListDialogOnClickListener l) {
		super(context, style);

		mListDialogOnClickListener = l;
		mListAdapter = new TextAdapter(context, items);
		initDialog();
	}

	public ListDialog(Context context, ListAdapter adapter,
			ListDialogOnClickListener l) {
		this(context, R.style.defaultDialogTheme, adapter, l);
	}

	public ListDialog(Context context, int style, ListAdapter adapter,
			ListDialogOnClickListener l) {
		super(context, style);

		mListDialogOnClickListener = l;
		initDialog();
	}

	protected ListDialog(Context context) {
		this(context, R.style.defaultDialogTheme);
	}

	protected ListDialog(Context context, int style) {
		super(context, style);

		initDialog();
	}

	private void initDialog() {
		setContentView(R.layout.widget_list_dialog);

		mListView = (ListView) findViewById(R.id.lv_widgetListDialog_list);
		mListView.setOnItemClickListener(mOnItemClickListener);

		if (mListAdapter != null) {
			mListView.setAdapter(mListAdapter);
		}
	}

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (mListDialogOnClickListener != null) {
				mListDialogOnClickListener.onItemClick(ListDialog.this,
						position);
			}

			cancel();
		}

	};

	/**
	 * 设置列表的分割线
	 * 
	 * @param resId
	 */
	public void setListDivider(int resId) {
		Drawable drawable = mContext.getResources().getDrawable(resId);
		setListDivider(drawable);
	}

	/**
	 * 设置列表的分割线
	 * 
	 * @param divider
	 */
	public void setListDivider(Drawable divider) {
		mListView.setDivider(divider);
	}

	/**
	 * 设置列表的选择器
	 * 
	 * @param resId
	 */
	public void setListSelector(int resId) {
		mListView.setSelector(resId);
	}

	/**
	 * 设置列表的选择器
	 * 
	 * @param resId
	 */
	public void setListSelector(Drawable sel) {
		mListView.setSelector(sel);
	}

	/**
	 * 设置列表的适配器
	 * 
	 * @param adapter
	 */
	protected void setListAdapter(ListAdapter adapter) {
		mListView.setAdapter(adapter);
	}

	/**
	 * 设置列表项的点击监听器
	 * 
	 * @param adapter
	 */
	protected void setListOnItemClickListener(OnItemClickListener l) {
		mListView.setOnItemClickListener(l);
	}

	public interface ListDialogOnClickListener {

		/**
		 * 选择监听器
		 * 
		 * @param dialog
		 * @param position
		 */
		public void onItemClick(DialogInterface dialog, int position);
	}

}

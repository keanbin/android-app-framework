package com.keanbin.framework.adapter;

import java.util.HashMap;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keanbin.app.R;
import com.keanbin.framework.base.BaseAdapter;

/**
 * 复选框列表适配器
 * 
 * @author keanbin
 * 
 */
public class MultiChoiceAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private final Context mContext;
	private CharSequence[] mItems;
	private boolean[] mCheckedItems;
	private HashMap<ImageView, Integer> mItemToImageViewMap;
	private Drawable mNormalDrawable;
	private Drawable mSelectedDrawable;

	public MultiChoiceAdapter(Context context, CharSequence[] items,
			boolean[] checkedItems) {
		this.mContext = context;

		mItems = items;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mCheckedItems = checkedItems;
		mItemToImageViewMap = new HashMap<ImageView, Integer>();

		mNormalDrawable = mContext.getResources().getDrawable(
				R.drawable.default_checkbox_normal);
		mSelectedDrawable = mContext.getResources().getDrawable(
				R.drawable.default_checkbox_selected);
	}

	public void setNormalDrawable(Drawable drawable) {
		mNormalDrawable = drawable;
	}

	public void setNormalDrawable(int resId) {
		mNormalDrawable = mContext.getResources().getDrawable(resId);
	}

	public void setSelectedDrawable(Drawable drawable) {
		mSelectedDrawable = drawable;
	}

	public void setSelectedDrawable(int resId) {
		mSelectedDrawable = mContext.getResources().getDrawable(resId);
	}

	public int getCount() {
		return mItems.length;
	}

	public Object getItem(int position) {
		return mItems[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		CellHolder cellHolder;

		if (convertView == null) {
			cellHolder = new CellHolder();
			convertView = mInflater.inflate(R.layout.item_multi_choice_list,
					null);

			cellHolder.textTv = (TextView) convertView
					.findViewById(R.id.tv_itemMultiChoiceList_text);
			cellHolder.iconIv = (ImageView) convertView
					.findViewById(R.id.iv_itemMultiChoiceList_select);

			convertView.setTag(cellHolder);
		} else {
			cellHolder = (CellHolder) convertView.getTag();
		}

		cellHolder.textTv.setText(mItems[position]);

		if (mCheckedItems != null && mCheckedItems[position]) {
			cellHolder.iconIv.setBackgroundDrawable(mSelectedDrawable);
		} else {
			cellHolder.iconIv.setBackgroundDrawable(mNormalDrawable);
		}

		mItemToImageViewMap.put(cellHolder.iconIv, position);

		return convertView;
	}

	public boolean setCheck(int position) {

		if (mCheckedItems == null || position < 0 || position >= mItems.length) {
			return false;
		}

		for (Entry<ImageView, Integer> entry : mItemToImageViewMap.entrySet()) {
			if (entry.getValue().intValue() == position) {
				ImageView imageView = entry.getKey();
				if (imageView != null) {
					if (mCheckedItems[position]) {
						mCheckedItems[position] = false;
						imageView.setBackgroundDrawable(mNormalDrawable);
					} else {
						mCheckedItems[position] = true;
						imageView.setBackgroundDrawable(mSelectedDrawable);
					}
				}
			}
		}
		
		return mCheckedItems[position];
	}

	private class CellHolder {
		TextView textTv;
		ImageView iconIv;
	}
}

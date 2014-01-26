package com.keanbin.framework.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * 单选框列表适配器
 * 
 * @author keanbin
 * 
 */
public class SingleChoiceAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private final Context mContext;
	private CharSequence[] mItems;
	private int mSelectIndex;
	private List<ImageView> mSelectedIvList;
	private HashMap<ImageView, Integer> mItemToImageViewMap;
	private Drawable mNormalDrawable;
	private Drawable mSelectedDrawable;

	public SingleChoiceAdapter(Context context, CharSequence[] items, int select) {
		this.mContext = context;

		mItems = items;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mSelectIndex = select;
		mSelectedIvList = new ArrayList<ImageView>();
		mItemToImageViewMap = new HashMap<ImageView, Integer>();

		mNormalDrawable = mContext.getResources().getDrawable(
				R.drawable.default_radio_normal);
		mSelectedDrawable = mContext.getResources().getDrawable(
				R.drawable.default_radio_selected);
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
			convertView = mInflater.inflate(R.layout.item_single_choice_list,
					null);

			cellHolder.textTv = (TextView) convertView
					.findViewById(R.id.tv_itemSingleChoiceList_text);
			cellHolder.iconIv = (ImageView) convertView
					.findViewById(R.id.iv_itemSingleChoiceList_select);

			convertView.setTag(cellHolder);
		} else {
			cellHolder = (CellHolder) convertView.getTag();
		}

		cellHolder.textTv.setText(mItems[position]);

		if (mSelectIndex == position) {
			cellHolder.iconIv.setBackgroundDrawable(mSelectedDrawable);
			mSelectedIvList.add(cellHolder.iconIv);
		} else {
			cellHolder.iconIv.setBackgroundDrawable(mNormalDrawable);
			mSelectedIvList.remove(cellHolder.iconIv);
		}

		mItemToImageViewMap.put(cellHolder.iconIv, position);

		return convertView;
	}

	public void setSelect(int position) {

		if (position < 0 || position >= mItems.length) {
			return;
		}

		mSelectIndex = position;

		for (int i = 0; i < mSelectedIvList.size(); i++) {
			ImageView imageView = mSelectedIvList.get(i);
			imageView.setBackgroundDrawable(mNormalDrawable);
		}

		mSelectedIvList.clear();

		for (Entry<ImageView, Integer> entry : mItemToImageViewMap.entrySet()) {
			if (entry.getValue().intValue() == position) {
				ImageView imageView = entry.getKey();
				if (imageView != null) {
					imageView.setBackgroundDrawable(mSelectedDrawable);
					mSelectedIvList.add(imageView);
				}
			}
		}
	}

	private class CellHolder {
		TextView textTv;
		ImageView iconIv;
	}
}

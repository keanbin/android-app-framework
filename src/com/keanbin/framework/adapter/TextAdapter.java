package com.keanbin.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keanbin.app.R;
import com.keanbin.framework.base.BaseAdapter;

/**
 * 文本列表适配器
 * 
 * @author keanbin
 * 
 */
public class TextAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private final Context mContext;
	private CharSequence[] mItems;

	public TextAdapter(Context context, CharSequence[] items) {
		this.mContext = context;

		mItems = items;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			convertView = mInflater.inflate(R.layout.item_text_list, null);

			cellHolder.textTv = (TextView) convertView
					.findViewById(R.id.tv_itemTextList_text);

			convertView.setTag(cellHolder);
		} else {
			cellHolder = (CellHolder) convertView.getTag();
		}

		cellHolder.textTv.setText(mItems[position]);

		return convertView;
	}

	private class CellHolder {
		TextView textTv;
	}

}

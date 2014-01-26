package com.keanbin.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keanbin.app.R;
import com.keanbin.app.entity.ListTemplateInfo;
import com.keanbin.framework.bitmap.FinalBitmap;

/**
 * 收益记录适配器
 * 
 * @author keanbin
 * 
 */
public class ListTemplateListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private final Context mContext;
	private List<ListTemplateInfo> mIncomeRecordList;
	private FinalBitmap mFinalBitmap;

	public ListTemplateListAdapter(Context context,
			List<ListTemplateInfo> incomeRecordList) {
		this.mContext = context;

		mIncomeRecordList = incomeRecordList;

		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// 初始化FinalBitmap模块
		mFinalBitmap = FinalBitmap.create(mContext);
		mFinalBitmap.configLoadingImage(R.drawable.default_head);
		mFinalBitmap.configLoadfailImage(R.drawable.default_head);

	}

	public int getCount() {
		return mIncomeRecordList.size();
	}

	public Object getItem(int position) {
		return mIncomeRecordList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		CellHolder cellHolder;
		if (convertView == null) {
			cellHolder = new CellHolder();
			convertView = mInflater.inflate(R.layout.item_list_template_list,
					null);

			cellHolder.imgIv = (ImageView) convertView
					.findViewById(R.id.iv_itemIncomeRecordList_img);
			cellHolder.nameTv = (TextView) convertView
					.findViewById(R.id.tv_itemIncomeRecordList_name);
			cellHolder.goldTv = (TextView) convertView
					.findViewById(R.id.tv_itemIncomeRecordList_gold);
			cellHolder.timeTv = (TextView) convertView
					.findViewById(R.id.tv_itemIncomeRecordList_time);

			convertView.setTag(cellHolder);
		} else {
			cellHolder = (CellHolder) convertView.getTag();
		}

		ListTemplateInfo incomeRecord = mIncomeRecordList.get(position);

		cellHolder.nameTv.setText(incomeRecord.getName());
		cellHolder.goldTv.setText(incomeRecord.getGoldDescribe(mContext));
		cellHolder.timeTv.setText(incomeRecord.getTimeDescribe());

		mFinalBitmap.display(cellHolder.imgIv, incomeRecord.getIcon());

		return convertView;
	}

	private class CellHolder {
		ImageView imgIv;
		TextView nameTv;
		TextView goldTv;
		TextView timeTv;
	}
}

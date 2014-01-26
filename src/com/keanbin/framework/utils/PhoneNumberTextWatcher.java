package com.keanbin.framework.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.keanbin.framework.base.BaseClass;
import com.keanbin.framework.utils.FormatCheck;
import com.keanbin.framework.utils.WidgetUtil;

/**
 * 电话号码观察者
 * 
 * @author keanbin
 * 
 */
public class PhoneNumberTextWatcher extends BaseClass implements TextWatcher {

	private EditText mEditText;

	public PhoneNumberTextWatcher(EditText view) {
		mEditText = view;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

		log("start = " + start + ", before = " + before + ", count = " + count);

		String etText = s.toString();
		String newText = FormatCheck.formatPhoneNumber(etText);
		if (newText != null && !newText.equals(etText)) {
			mEditText.setText(newText);

			WidgetUtil.setEditTextCursorEnd(mEditText);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

}

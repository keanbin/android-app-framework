package com.keanbin.framework.utils;

import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.Selection;
import android.text.Spannable;
import android.widget.EditText;

public class WidgetUtil {

	/**
	 * 设置EditText的光标在文本末尾
	 * 
	 * @param editText
	 */
	public static void setEditTextCursorEnd(EditText editText) {
		if (editText == null) {
			return;
		}

		CharSequence text = editText.getText();
		if (text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			Selection.setSelection(spanText, text.length());
		}
	}

	/**
	 * 设置EditText的最大长度
	 * 
	 * @param editText
	 * @param length
	 */
	public static void setEditTextMaxLength(EditText editText, int length) {
		if (editText == null || length < 0) {
			return;
		}

		InputFilter[] filters = { new LengthFilter(length) };
		editText.setFilters(filters);
	}
}

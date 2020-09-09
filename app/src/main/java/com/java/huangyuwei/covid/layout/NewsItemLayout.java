package com.java.huangyuwei.covid.layout;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsItemLayout extends LinearLayout {
	public NewsItemLayout(final Context context, String title, String time){
		super(context);

		setBackgroundColor(Color.rgb(240, 240, 240));
		setOrientation(VERTICAL);

		TextView titleText = new TextView(context);
		titleText.setText(title);
		titleText.setTextSize(18);
		titleText.setTextColor(Color.DKGRAY);
		titleText.getPaint().setFakeBoldText(true);

		TextView timeText = new TextView(context);
		timeText.setText(time);
		timeText.setTextSize(15);
		timeText.setTextColor(Color.rgb(128, 128, 255));

		LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		textParams.setMargins(15, 15, 15, 15);
		titleText.setLayoutParams(textParams);
		timeText.setLayoutParams(textParams);

		addView(titleText);
		addView(timeText);
	}
}

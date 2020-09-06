package com.java.huangyuwei.covid.layout;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class GraphItemLayout extends LinearLayout {

	public GraphItemLayout(Context context, String entity, String description) {
		super(context);
		setBackgroundColor(Color.rgb(240, 240, 240));

		setOrientation(VERTICAL);

		TextView entityTitle = new TextView(context);
		entityTitle.setText(entity);
		entityTitle.setTextSize(20);
		entityTitle.getPaint().setFakeBoldText(true);

		TextView entityDescription = new TextView(context);
		entityDescription.setText("".equals(description) ? "暂无描述" : description);
		entityDescription.setTextSize(15);
		entityDescription.setTextColor(Color.rgb(128, 128, 255));

		LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		textParams.setMargins(15, 15, 15, 15);
		entityTitle.setLayoutParams(textParams);
		entityDescription.setLayoutParams(textParams);

		addView(entityTitle);
		addView(entityDescription);
	}
}

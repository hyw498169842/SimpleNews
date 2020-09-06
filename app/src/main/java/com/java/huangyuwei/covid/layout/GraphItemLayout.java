package com.java.huangyuwei.covid.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.java.huangyuwei.GraphDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GraphItemLayout extends LinearLayout {

	public GraphItemLayout(final Context context, JSONObject object) throws JSONException {
		super(context);

		final double hot = object.getDouble("hot");
		final String entity = object.getString("label");
		JSONObject abstractInfo = object.getJSONObject("abstractInfo");
		final String description = abstractInfo.getString("enwiki") +
			abstractInfo.getString("baidu") +
			abstractInfo.getString("zhwiki");
		final JSONObject properties = abstractInfo.getJSONObject("COVID").getJSONObject("properties");
		final JSONArray relations = abstractInfo.getJSONObject("COVID").getJSONArray("relations");

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

		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context, GraphDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putDouble("hot", hot);
				bundle.putCharSequence("title", entity);
				bundle.putCharSequence("description", description);
				bundle.putCharSequence("properties", properties.toString());
				bundle.putCharSequence("relations", relations.toString());
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
	}
}
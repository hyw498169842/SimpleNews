package com.java.huangyuwei.covid.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.java.huangyuwei.GraphDetailActivity;
import com.java.huangyuwei.MainActivity;
import com.java.huangyuwei.covid.bean.GraphItemBean;

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
		entityTitle.setTextColor(Color.DKGRAY);
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
				GraphItemBean bean = GraphItemBean.getInstance();
				bean.setHot(hot);
				bean.setTitle(entity);
				bean.setDescription(description);
				bean.setProperties(properties.toString());
				bean.setRelations(relations.toString());
				((MainActivity) context).startActivityForResult(intent, 1);
			}
		});
	}
}

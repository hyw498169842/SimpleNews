package com.java.huangyuwei;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.java.huangyuwei.covid.bean.GraphItemBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class GraphDetailActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph_item);

		Toolbar toolbar = findViewById(R.id.toolbar_g);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		try {
			GraphItemBean bean = GraphItemBean.getInstance();
			double hot = bean.getHot();
			String title = bean.getTitle();
			String description = bean.getDescription();
			JSONObject properties = new JSONObject(bean.getProperties());
			JSONArray relations = new JSONArray(bean.getRelations());
			// 页面标题
			toolbar.setTitle(title);
			LinearLayout layout = findViewById(R.id.graph_detail_layout);
			layout.setOrientation(LinearLayout.VERTICAL);
			// 实体标题
			TextView entityTitle = new TextView(this);
			entityTitle.setText(title);
			entityTitle.setTextSize(40);
			entityTitle.setTextColor(Color.DKGRAY);
			entityTitle.getPaint().setFakeBoldText(true);
			// 实体热度
			TextView entityType = new TextView(this);
			entityType.setText(String.format(Locale.CHINA, "当前实体热度：%.6f", hot));
			entityType.setTextColor(Color.rgb(160, 160, 160));
			entityType.setTextSize(18);
			// 实体描述
			TextView entityDescription = new TextView(this);
			entityDescription.setText("".equals(description) ? "暂无描述" : description);
			entityDescription.setTextSize(20);
			entityDescription.setTextColor(Color.rgb(128, 128, 255));
			// 为上面的文本设置布局
			LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			textParams.setMargins(50, 50, 50, 0);
			entityTitle.setLayoutParams(textParams);
			entityType.setLayoutParams(textParams);
			entityDescription.setLayoutParams(textParams);
			// 添加到layout中
			layout.addView(entityTitle);
			layout.addView(entityType);
			layout.addView(entityDescription);
			// 实体属性
			if(properties.names() != null) {
				// 属性标志
				TextView propertyStart = new TextView(this);
				propertyStart.setText("属性");
				propertyStart.setTextSize(30);
				propertyStart.setTextColor(Color.DKGRAY);
				propertyStart.getPaint().setFakeBoldText(true);
				LinearLayout.LayoutParams propertyParams = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				propertyParams.setMargins(50, 80, 50, 0);
				propertyStart.setLayoutParams(propertyParams);
				layout.addView(propertyStart);
				// 各属性值
				JSONArray propertyNames = properties.names();
				for(int i = 0; i < propertyNames.length(); i++) {
					String name = propertyNames.getString(i);
					String value = properties.getString(name);
					String html = String.format(Locale.CHINA, "<font color='#A7A7A7'><b>%s：</b></font><font color='#8080FF'>%s</font>", name, value);
					TextView currentProperty = new TextView(this);
					currentProperty.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));
					currentProperty.setTextSize(20);
					LinearLayout.LayoutParams currentParams = new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
					currentParams.setMargins(50, 20, 50, 0);
					currentProperty.setLayoutParams(currentParams);
					layout.addView(currentProperty);
				}
			}
			// 实体关系
			if(relations.length() > 0) {
				// 关系标志
				TextView relationStart = new TextView(this);
				relationStart.setText("关系");
				relationStart.setTextSize(30);
				relationStart.setTextColor(Color.DKGRAY);
				relationStart.getPaint().setFakeBoldText(true);
				LinearLayout.LayoutParams relationParams = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				relationParams.setMargins(50, 80, 50, 0);
				relationStart.setLayoutParams(relationParams);
				layout.addView(relationStart);
				// 各关系值
				GridLayout gridLayout = new GridLayout(this);
				for(int i = 0; i < relations.length(); i++) {
					// 获取所需信息
					JSONObject currentObject = relations.getJSONObject(i);
					String relation = currentObject.getString("relation");
					String label = currentObject.getString("label");
					boolean forward = currentObject.getBoolean("forward");
					// 关系文本框
					TextView relationText = new TextView(this);
					relationText.setMaxEms(10);
					relationText.setText(relation);
					relationText.setTextSize(20);
					relationText.setTextColor(Color.rgb(128, 128, 128));
					relationText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
					GridLayout.LayoutParams relationTextParams = new GridLayout.LayoutParams(
						GridLayout.spec(i, GridLayout.CENTER), GridLayout.spec(0, GridLayout.CENTER, 1));
					relationTextParams.setMargins(0, 0, 0, 50);
					relationText.setLayoutParams(relationTextParams);
					gridLayout.addView(relationText);
					// 图片箭头
					ImageView imageView = new ImageView(this);
					imageView.setImageResource(forward ? R.drawable.right: R.drawable.left);
					imageView.setAdjustViewBounds(true);
					imageView.setMaxHeight(80);
					GridLayout.LayoutParams imageViewParams = new GridLayout.LayoutParams(
						GridLayout.spec(i, GridLayout.CENTER), GridLayout.spec(1, GridLayout.CENTER, 1));
					imageViewParams.setMargins(0, 0, 0, 50);
					imageView.setLayoutParams(imageViewParams);
					gridLayout.addView(imageView);
					// 标签文本框
					final TextView labelText = new TextView(this);
					labelText.setMaxEms(10);
					labelText.setText(label);
					labelText.setTextSize(20);
					labelText.setTextColor(Color.DKGRAY);
					labelText.getPaint().setAntiAlias(true);
					labelText.getPaint().setFakeBoldText(true);
					labelText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
					labelText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
					GridLayout.LayoutParams labelTextParams = new GridLayout.LayoutParams(
						GridLayout.spec(i, GridLayout.CENTER), GridLayout.spec(2, GridLayout.CENTER, 1));
					labelTextParams.setMargins(0, 0, 0, 50);
					labelText.setLayoutParams(labelTextParams);
					gridLayout.addView(labelText);
					// 监听标签点击事件
					labelText.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							Intent request = new Intent();
							request.putExtra("entity", labelText.getText());
							setResult(1, request);
							finish();
						}
					});
				}
				LinearLayout.LayoutParams gridLayoutParams = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				gridLayoutParams.setMargins(50, 20, 50, 0);
				gridLayout.setLayoutParams(gridLayoutParams);
				layout.addView(gridLayout);
			}
		} catch (JSONException | NullPointerException e) {
			e.printStackTrace();
		}
	}
}

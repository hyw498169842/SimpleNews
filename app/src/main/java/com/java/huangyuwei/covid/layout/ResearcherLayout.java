package com.java.huangyuwei.covid.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.java.huangyuwei.covid.ResearcherDetailActivity;
import com.java.huangyuwei.covid.bean.ResearcherItemBean;

import org.json.JSONException;
import org.json.JSONObject;

public class ResearcherLayout extends LinearLayout {
	public ResearcherLayout(final Context context, JSONObject object) throws JSONException {
		super(context);

		final String iconUrl = object.getString("icon");
		final String name = object.getString("name") + " " +object.getString("name_zh");
		final String affiliation = object.getString("affiliation");
		final String position = object.getString("position");
		final String email = object.getString("email");
		final String bio = object.getString("bio");
		final String edu = object.getString("edu");
		final String work = object.getString("work");
		final JSONObject indices = object.getJSONObject("indices");
		final JSONObject tags = object.getJSONObject("tags");

		setBackgroundColor(Color.rgb(240, 240, 240));
		// 姓名文本框
		TextView nameText = new TextView(context);
		nameText.setText(name);
		nameText.setTextSize(20);
		nameText.setTextColor(Color.DKGRAY);
		nameText.getPaint().setFakeBoldText(true);
		// 单位文本框
		TextView affiliationText = new TextView(context);
		affiliationText.setText(affiliation);
		affiliationText.setTextSize(15);
		affiliationText.setTextColor(Color.rgb(128, 128, 255));
		// 职位文本框
		TextView positionText = new TextView(context);
		positionText.setText(position);
		positionText.setTextSize(15);
		positionText.setTextColor(Color.rgb(128, 128, 255));
		// 电子邮件文本框
		TextView emailText = new TextView(context);
		emailText.setText(email);
		emailText.setTextSize(15);
		emailText.setTextColor(Color.DKGRAY);
		// 设置layout参数
		LinearLayout.LayoutParams infoParams = new LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		infoParams.setMargins(15, 15, 15, 15);
		nameText.setLayoutParams(infoParams);
		affiliationText.setLayoutParams(infoParams);
		positionText.setLayoutParams(infoParams);
		emailText.setLayoutParams(infoParams);
		// 以上文本框纵向布局
		LinearLayout infoLayout = new LinearLayout(context);
		infoLayout.setOrientation(VERTICAL);
		infoLayout.addView(nameText);
		infoLayout.addView(affiliationText);
		infoLayout.addView(positionText);
		infoLayout.addView(emailText);
		// 学者头像
		ImageView icon = new ImageView(context);
		RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
		Glide.with(context).load(iconUrl).apply(options).into(icon);
		icon.setAdjustViewBounds(true);
		icon.setMaxWidth(250);
		// 头像与文本框横向布局
		LinearLayout.LayoutParams fullInfoParams = new LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		fullInfoParams.setMargins(10, 10, 10, 10);
		icon.setLayoutParams(fullInfoParams);
		infoLayout.setLayoutParams(fullInfoParams);
		addView(icon);
		addView(infoLayout);

		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				ResearcherItemBean item = ResearcherItemBean.getInstance();
				item.setName(name);
				item.setAffiliation(affiliation);
				item.setPosition(position);
				item.setEmail(email);
				item.setIconUrl(iconUrl);
				item.setIndices(indices.toString());
				item.setTags(tags.toString());
				item.setBio(bio);
				item.setEdu(edu);
				item.setWork(work);
				Intent intent = new Intent(context, ResearcherDetailActivity.class);
				context.startActivity(intent);
			}
		});
	}
}

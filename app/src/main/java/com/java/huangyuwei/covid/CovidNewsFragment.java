package com.java.huangyuwei.covid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.java.huangyuwei.R;
import com.java.huangyuwei.covid.layout.NewsItemLayout;
import com.java.huangyuwei.covid.util.FileContentReader;
import com.java.huangyuwei.news.NewsLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class CovidNewsFragment extends Fragment {
	final int CLUSTER_COUNT = 50;
	private Activity savedActivity;

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		savedActivity = (Activity)context;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.covid_news_fragment, container, false);

		final TextView keywordText = view.findViewById(R.id.keywords);
		keywordText.setTextSize(15);
		keywordText.setTextColor(Color.rgb(0xA0, 0x50, 0xE0));
		keywordText.getPaint().setFakeBoldText(true);
		final LinearLayout layout = view.findViewById(R.id.covid_news_layout);

		Button button = view.findViewById(R.id.random_cluster);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					layout.removeAllViews();

					int clusterId = new Random().nextInt(CLUSTER_COUNT);
					InputStream in = getResources().openRawResource(R.raw.clusters);
					JSONArray object = new JSONArray(FileContentReader.getContent(in));
					JSONObject target = object.getJSONObject(clusterId);

					keywordText.setText("关键词：");
					JSONArray keywordArray = target.getJSONArray("keywords");
					for(int i = 0; i < keywordArray.length(); i++) {
						keywordText.append(keywordArray.getString(i) + " ");
					}

					JSONArray newsList = target.getJSONArray("news_list");
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					params.setMargins(10, 20 , 10 , 20);
					for(int i = 0; i < newsList.length(); i++) {
						String title = newsList.getJSONObject(i).getString("title");
						String time = newsList.getJSONObject(i).getString("time");

						NewsItemLayout newLayout = new NewsItemLayout(savedActivity, title, time);
						newLayout.setLayoutParams(params);
						layout.addView(newLayout);
					}
				} catch(JSONException | IOException e) {
					e.printStackTrace();
				}
			}
		});

		button.callOnClick();

		return view;
	}
}

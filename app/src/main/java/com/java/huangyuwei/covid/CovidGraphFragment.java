package com.java.huangyuwei.covid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.huangyuwei.R;
import com.java.huangyuwei.covid.layout.GraphItemLayout;
import com.java.huangyuwei.covid.util.UrlContentReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class CovidGraphFragment extends Fragment {
	private View savedView;
	private Activity savedActivity;

	private Handler handler;
	private ArrayList<String> entities = new ArrayList<>();

	private final int RANDOM_ITEM_COUNT = 10;
	private final String url = "https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity=";

	private String readJson() throws IOException {
		InputStream stream = getResources().openRawResource(R.raw.entity_list);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder builder = new StringBuilder();
		for(String buffer = reader.readLine(); buffer != null; ) {
			builder.append(buffer);
			builder.append("\n");
			buffer = reader.readLine();
		}
		return builder.toString();
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		savedActivity = (Activity)context;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(savedView != null) {
			return savedView;
		}

		savedView = inflater.inflate(R.layout.covid_graph_fragment, container, false);

		handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(@NonNull Message message) {
				if(message.what == 1) {
					ArrayList<String[]> info = (ArrayList<String[]>) message.obj;
					LinearLayout layout = savedView.findViewById(R.id.graph_linear_layout);
					layout.removeAllViews();
					for(String[] itemInfo: info) {
						GraphItemLayout newLayout = new GraphItemLayout(savedActivity, itemInfo[0], itemInfo[1]);
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
						params.setMargins(10, 10 , 10 , 10);
						newLayout.setLayoutParams(params);
						layout.addView(newLayout);
					}
					if(info.size() == 0) {
						TextView noItemText = new TextView(savedActivity);
						noItemText.setText("很抱歉，没有找到相关内容，请换个关键词再试吧。");
						noItemText.setTextSize(15);
						layout.addView(noItemText);
					}
				}
				return true;
			}
		});

		try {
			JSONArray jsonArray = new JSONArray(readJson());
			for(int i = 0; i < jsonArray.length(); i++) {
				entities.add(jsonArray.getString(i));
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		Button button = savedView.findViewById(R.id.feeling_lucky);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new Thread() {
					@Override
					public void run() {
						try {
							HashSet<Integer> selectedIndex = new HashSet<>();
							while(selectedIndex.size() < RANDOM_ITEM_COUNT) {
								selectedIndex.add(new Random().nextInt(entities.size()));
							}
							ArrayList<String[]> info = new ArrayList<>();
							for(Integer index: selectedIndex) {
								String[] currentInfo = new String[2];
								currentInfo[0] = entities.get(index);
								JSONObject object = new JSONObject(UrlContentReader.getContent(url + currentInfo[0]));
								JSONObject abstractInfo = object.getJSONArray("data")
									.getJSONObject(0)
									.getJSONObject("abstractInfo");
								currentInfo[1] = abstractInfo.getString("enwiki") +
									abstractInfo.getString("baidu") +
									abstractInfo.getString("zhwiki");
								info.add(currentInfo);
							}
							Message message = new Message();
							message.what = 1;
							message.obj = info;
							handler.sendMessage(message);
						} catch(IOException | JSONException e) {
							e.printStackTrace();
						}
					}
				}.start();
			}
		});

		final SearchView searchView = savedView.findViewById(R.id.search_view);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(final String s) {
				new Thread() {
					@Override
					public void run() {
						try {
							JSONObject object = new JSONObject(UrlContentReader.getContent(url + s));
							JSONArray data = object.getJSONArray("data");
							ArrayList<String[]> info = new ArrayList<>();
							for(int i = 0; i < data.length(); i++) {
								String[] currentInfo = new String[2];
								JSONObject itemObject = data.getJSONObject(i);
								currentInfo[0] = itemObject.getString("label");
								JSONObject abstractInfo = itemObject.getJSONObject("abstractInfo");
								currentInfo[1] = abstractInfo.getString("enwiki") +
									abstractInfo.getString("baidu") +
									abstractInfo.getString("zhwiki");
								info.add(currentInfo);
							}
							Message message = new Message();
							message.what = 1;
							message.obj = info;
							handler.sendMessage(message);
						} catch (JSONException | IOException e) {
							e.printStackTrace();
						}
					}
				}.start();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String s) {
				return false;
			}
		});

		button.callOnClick();

		return savedView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(savedView != null && savedView.getParent() != null) {
			((ViewGroup)savedView.getParent()).removeView(savedView);
		}
	}

}

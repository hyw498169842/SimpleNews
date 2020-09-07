package com.java.huangyuwei.covid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.java.huangyuwei.R;
import com.java.huangyuwei.covid.layout.ResearcherLayout;
import com.java.huangyuwei.covid.util.FileContentReader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class CovidResearcherFragment extends Fragment {
	private Activity savedActivity;

	private void showItems(int jsonId) {
		try {
			InputStream in = getResources().openRawResource(jsonId);
			JSONArray array = new JSONArray(FileContentReader.getContent(in));
			LinearLayout layout = savedActivity.findViewById(R.id.researcher_layout);
			layout.removeAllViews();
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(10, 10 , 10 , 10);
			for(int i = 0; i < array.length(); i++) {
				ResearcherLayout newLayout = new ResearcherLayout(savedActivity, array.getJSONObject(i));
				newLayout.setLayoutParams(params);
				layout.addView(newLayout);
			}
		} catch(IOException | JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		savedActivity = (Activity)context;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.covid_researcher_fragment, container, false);

		TabLayout tabLayout = view.findViewById(R.id.researcher_tab);
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				String type = (String) tab.getText();
				if("高关注学者".equals(type)) {
					showItems(R.raw.not_passed_away);
				} else {
					showItems(R.raw.passed_away);
				}
			}
			@Override
			public void onTabUnselected(TabLayout.Tab tab) {}
			@Override
			public void onTabReselected(TabLayout.Tab tab) {}
		});

		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		showItems(R.raw.not_passed_away);
	}
}

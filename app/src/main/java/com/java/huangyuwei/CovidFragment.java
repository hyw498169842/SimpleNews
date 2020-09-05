package com.java.huangyuwei;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import com.java.huangyuwei.covid.CovidDataFragment;
import com.java.huangyuwei.covid.CovidGraphFragment;
import com.java.huangyuwei.covid.CovidNewsFragment;
import com.java.huangyuwei.covid.CovidResearcherFragment;

public class CovidFragment extends Fragment {

	private ArrayList<Fragment> fragments;
	private ArrayList<String> titles;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.covid_fragment, container, false);

		fragments = new ArrayList<>();
		fragments.add(new CovidDataFragment());
		fragments.add(new CovidGraphFragment());
		fragments.add(new CovidNewsFragment());
		fragments.add(new CovidResearcherFragment());

		titles = new ArrayList<>();
		titles.add("疫情数据");
		titles.add("疫情图谱");
		titles.add("新闻聚类");
		titles.add("知疫学者");

		TabLayout tabLayout = view.findViewById(R.id.covid_tab);
		ViewPager viewPager = view.findViewById(R.id.covid_pager);

		FragmentPagerAdapter adapter = new FragmentPagerAdapter(
			getChildFragmentManager(),
			FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
		) {
			@NonNull
			@Override
			public Fragment getItem(int position) {
				return fragments.get(position);
			}

			@Override
			public int getCount() {
				return fragments.size();
			}

			@Nullable
			@Override
			public CharSequence getPageTitle(int position) {
				return titles.get(position);
			}
		};

		tabLayout.setupWithViewPager(viewPager);
		viewPager.setAdapter(adapter);

		return view;
	}
}

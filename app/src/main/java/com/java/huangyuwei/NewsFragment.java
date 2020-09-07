package com.java.huangyuwei;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import com.java.huangyuwei.news.TypeFragment;

public class NewsFragment extends Fragment {
	private ArrayList<Fragment> fragments;
	private ArrayList<String> titles;
	private TabLayout tabLayout;
	private ViewPager viewPager;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.news_fragment, container, false);
		// search for news
		Toolbar toolbar = view.findViewById(R.id.toolbar);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), SearchActivity.class);
				startActivity(intent);
			}
		});

		fragments = new ArrayList<>();
		fragments.add(new TypeFragment("all"));
		fragments.add(new TypeFragment("event"));
		fragments.add(new TypeFragment("points"));
		fragments.add(new TypeFragment("news"));
		fragments.add(new TypeFragment("paper"));

		titles = new ArrayList<>();
		titles.add("All");
		titles.add("Event");
		titles.add("Points");
		titles.add("News");
		titles.add("Paper");

		tabLayout = view.findViewById(R.id.news_tab);
		viewPager = view.findViewById(R.id.news_pager);

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
		tabLayout.addTab(tabLayout.newTab().setText("2331"));
		viewPager.setAdapter(adapter);
		return view;
	}
}

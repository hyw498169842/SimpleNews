package com.java.huangyuwei.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import com.java.huangyuwei.R;

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

		FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {
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

			@Override
			public int getItemPosition(@NonNull Object object) {
				return POSITION_NONE;
			}

			@Override
			public Parcelable saveState() {
				return null;
			}
		};

		tabLayout.setupWithViewPager(viewPager);
		viewPager.setAdapter(adapter);
		/*RelativeLayout.LayoutParams tabParams = new RelativeLayout.LayoutParams(view.getWidth() - 200, ViewGroup.LayoutParams.WRAP_CONTENT);
		tabParams.width
		tabLayout.setLayoutParams(tabParams);*/

		ImageView imageView = view.findViewById(R.id.more_icon);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent t = new Intent(getActivity(), ChipsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("All", false);
				bundle.putBoolean("Event", false);
				bundle.putBoolean("Points", false);
				bundle.putBoolean("News", false);
				bundle.putBoolean("Paper", false);

				for(String each_title : titles) {
					bundle.putBoolean(each_title, true);
				}
				t.putExtras(bundle);
				getActivity().startActivityForResult(t,2);
			}
		});
		return view;
	}
	public void setTitles(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5) {
		fragments.clear();
		titles.clear();
		if(b1) {
			fragments.add(new TypeFragment("all"));
			titles.add("All");
		}
		if(b2) {
			fragments.add(new TypeFragment("event"));
			titles.add("Event");
		}
		if(b3) {
			fragments.add(new TypeFragment("points"));
			titles.add("Points");
		}
		if(b4) {
			fragments.add(new TypeFragment("news"));
			titles.add("News");
		}
		if(b5) {
			fragments.add(new TypeFragment("paper"));
			titles.add("Paper");
		}
		viewPager.getAdapter().notifyDataSetChanged();
	}
}

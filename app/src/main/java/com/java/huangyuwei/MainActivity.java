package com.java.huangyuwei;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
	private NewsFragment newsFragment;
	private CovidFragment covidFragment;
	private UserFragment userFragment;
	private Thread mainThreadHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		newsFragment = new NewsFragment();
		covidFragment = new CovidFragment();
		userFragment = new UserFragment();

		FragmentManager manager =  getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.main_layout, newsFragment).commit();

		BottomNavigationView navigationView = findViewById(R.id.navigationView);
		navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				switch (item.getItemId()) {
					case R.id.item_news:
						transaction.replace(R.id.main_layout, newsFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit(); break;
					case R.id.item_covid:
						transaction.replace(R.id.main_layout, covidFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit(); break;
					case R.id.item_user:
						transaction.replace(R.id.main_layout, userFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit(); break;
				}
				return true;
			}
		});

	}
}
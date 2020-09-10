package com.java.huangyuwei;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.java.huangyuwei.covid.CovidFragment;
import com.java.huangyuwei.news.NewsFragment;
import com.java.huangyuwei.user.UserFragment;

public class MainActivity extends AppCompatActivity {
	private NewsFragment newsFragment;
	private CovidFragment covidFragment;
	private UserFragment userFragment;


	private void checkNeedPermissions(){
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
			!= PackageManager.PERMISSION_GRANTED
			|| ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
			!= PackageManager.PERMISSION_GRANTED) {
			//多个权限一起申请
			ActivityCompat.requestPermissions(this, new String[]{
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				Manifest.permission.READ_EXTERNAL_STORAGE
			}, 1);
		}
	}

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
		checkNeedPermissions();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1 && resultCode == 1) {
			SearchView searchView = findViewById(R.id.search_view);
			searchView.setQuery(data.getStringExtra("entity"), true);
		}
		else if(requestCode == 2 && resultCode == 2) {
			boolean b1 = data.getBooleanExtra("All", false);
			boolean b2 = data.getBooleanExtra("Event", false);
			boolean b3 = data.getBooleanExtra("Points", false);
			boolean b4 = data.getBooleanExtra("News", false);
			boolean b5 = data.getBooleanExtra("Paper", false);
			newsFragment.setTitles(b1, b2, b3, b4, b5);
		}
	}
}
package com.java.huangyuwei.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.huangyuwei.R;
import com.java.huangyuwei.news.newssaver.News2Json;
import com.java.huangyuwei.user.HistoryActivity;

public class UserFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.user_fragment, container, false);
		TextView historyView = view.findViewById(R.id.history);
		TextView clearHistoryView = view.findViewById(R.id.clear_history);
		clearHistoryView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				News2Json.delete(getContext().getFilesDir().getPath());
				Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
			}
		});
		historyView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), HistoryActivity.class);
				startActivity(intent);
			}
		});
		return view;
	}
}

package com.java.huangyuwei.user;

import android.graphics.Color;
import android.os.Bundle;

import com.java.huangyuwei.R;
import com.java.huangyuwei.news.NewsLayout;
import com.java.huangyuwei.news.newssaver.News2Json;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = findViewById(R.id.toolbar_h);

        final HistoryActivity _this = this;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _this.finish();
            }
        });

        final LinearLayout layout = findViewById(R.id.history_layout);
        String[][] s = News2Json.getNewsList(getFilesDir().getPath());
        if(s != null) {
            for (String[] strings : s) {
                NewsLayout newsLayout = new NewsLayout(this, strings[0], strings[1], strings[2], strings[3], strings[4]);
                newsLayout.setBackgroundColor(Color.rgb(0xe0, 0xe0, 0xe0));
                LinearLayout.LayoutParams newsParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                newsParams.setMargins(25, 15, 25, 15);
                newsLayout.setLayoutParams(newsParams);
                layout.addView(newsLayout);
            }
        }
        layout.invalidate();
    }
}
package com.java.huangyuwei;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.java.huangyuwei.news.NewsLayout;
import com.java.huangyuwei.news.newsparser.Searcher;
import com.java.huangyuwei.news.newssaver.News2Json;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final LinearLayout layout = findViewById(R.id.search_layout);
        final SearchActivity _this = this;
        final SearchView newsSearchView = findViewById(R.id.search_view);
        final Handler mainThreadHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                layout.removeAllViews();
                String[][] s = (String[][])msg.obj;
                String[][] newsList = News2Json.getNewsList(getFilesDir().getPath());
                if(s.length == 0) {
                    TextView t = new TextView(_this);
                    t.setText("很抱歉，没有找到相关内容，请换个关键词再试吧。");
                    t.setTextColor(Color.rgb(0,0,0));
                    t.setTextSize(15);
                    LinearLayout.LayoutParams newsParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    newsParams.setMargins(25,15,25,15);
                    t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    t.setLayoutParams(newsParams);
                    layout.addView(t);
                }
                for(int i = 0; i < s.length; i++) {
                    System.out.println(s[i][0]);
                    NewsLayout newsLayout = new NewsLayout(_this, s[i][0], s[i][1], s[i][2], s[i][3], s[i][4]);
                    boolean read = false;
                    if(newsList != null) {
                        for (int j = 0; j < newsList.length; j++) {
                            if (s[i][0].equals(newsList[j][0])) {
                                read = true;
                                break;
                            }
                        }
                    }
                    if(read) {
                        newsLayout.setBackgroundColor(Color.rgb(0xF4,0xF6,0xF8));
                    }
                    LinearLayout.LayoutParams newsParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    newsParams.setMargins(25,15,25,15);
                    newsLayout.setLayoutParams(newsParams);
                    layout.addView(newsLayout);
                }
                layout.invalidate();
                return false;
            }
        });
        newsSearchView.requestFocus();
        newsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Searcher searcher = new Searcher(mainThreadHandler, s);
                searcher.start();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}
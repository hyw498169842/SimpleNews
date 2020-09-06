package com.java.huangyuwei;

import android.graphics.Color;
import android.os.Bundle;

import com.blcodes.views.refresh.BounceCallBack;
import com.blcodes.views.refresh.BounceLayout;
import com.blcodes.views.refresh.EventForwardingHelper;
import com.blcodes.views.refresh.NormalBounceHandler;
import com.blcodes.views.refresh.footer.DefaultFooter;
import com.blcodes.views.refresh.header.DefaultHeader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.java.huangyuwei.news.NewsLayout;
import com.java.huangyuwei.news.TypeFragment;
import com.java.huangyuwei.news.newsparser.RefreshServer;
import com.java.huangyuwei.news.newsparser.Server;
import com.java.huangyuwei.news.newssaver.News2Json;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = findViewById(R.id.toolbar_h);

        FloatingActionButton fab = findViewById(R.id.fab_h);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final HistoryActivity _this = this;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _this.finish();
            }
        });


        final LinearLayout layout = (LinearLayout)findViewById(R.id.history_layout);
        String[][] s = News2Json.getNewsList(getFilesDir().getPath());
        if(s != null) {
            for (int i = 0; i < s.length; i++) {
                NewsLayout newsLayout = new NewsLayout(this, s[i][0], s[i][1], s[i][2], s[i][3], s[i][4]);
                newsLayout.setBackgroundColor(Color.rgb(0xF4, 0xF6, 0xF8));
                LinearLayout.LayoutParams newsParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                newsParams.setMargins(25, 15, 25, 15);
                newsLayout.setLayoutParams(newsParams);
                layout.addView(newsLayout);
            }
        }
        layout.invalidate();
    }
}
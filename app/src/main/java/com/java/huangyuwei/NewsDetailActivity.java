package com.java.huangyuwei;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);

        final NewsDetailActivity _this = this;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _this.finish();
            }
        });
        // fill the page
        TextView titleView = findViewById(R.id.news_detail_title);
        TextView dateView = findViewById(R.id.news_detail_date);
        TextView contentView = findViewById(R.id.news_detail_content);

        final Bundle bundle = getIntent().getExtras();
        titleView.setText(bundle.getString("title"));
        dateView.setText(bundle.getString("date"));
        contentView.setText(bundle.getString("content"));

        titleView.setTextSize(25);
        titleView.setTextColor(Color.rgb(0,0,0));
        titleView.setPadding(50,50,50,50);

        dateView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        dateView.setPadding(20,20,20,20);

        contentView.setTextSize(18);
        contentView.setTextColor(Color.rgb(0,0,0));
        contentView.setPadding(35,35,35,35);

        toolbar.setTitle(bundle.getString("source") + " " + bundle.getString("type"));
        toolbar.inflateMenu(R.menu.toolbar_menu);

        // 手动设置溢出菜单项的图标
        //toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.share_button));
        // 设置菜单点击事件监听
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.collect_button:
                        System.out.println("collect");
                        break;
                    case R.id.share_button:
                        String content = bundle.getString("content");
                        if(content.indexOf("。") < 0) {
                            String[] sentence = content.split("\\.");
                            int i = 0;
                            content = sentence[i++] + ".";
                            while(content.endsWith("Sept.") || content.endsWith("Jan.") || content.endsWith("Feb.") || content.endsWith("Mar.")
                            || content.endsWith("Apr.") || content.endsWith("May.") || content.endsWith("Jun.") || content.endsWith("Jul.")
                            || content.endsWith("Aug.") || content.endsWith("Oct.") || content.endsWith("Nov.") || content.endsWith("Dec.")) {
                                content = content + sentence[i++] + ".";
                            }
                        }
                        else {
                            content = content.split("。")[0] + "。";
                        }
                        share(bundle.getString("title"), bundle.getString("date"), content);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
    void share(String title, String date, String content) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);
        share_intent.setType("text/plain");
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        share_intent.putExtra(Intent.EXTRA_TEXT, "SimpleNews新闻\n" + title + "\n" + date + "\n" + "新闻摘要：" + content);
        share_intent = Intent.createChooser(share_intent, "分享");
        startActivity(share_intent);
    }
}
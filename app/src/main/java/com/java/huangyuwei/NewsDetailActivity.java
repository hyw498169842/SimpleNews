package com.java.huangyuwei;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

        Bundle bundle = getIntent().getExtras();
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
    }
}
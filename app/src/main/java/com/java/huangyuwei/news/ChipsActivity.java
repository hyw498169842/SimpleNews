package com.java.huangyuwei.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.java.huangyuwei.R;

public class ChipsActivity extends AppCompatActivity {
    private ChipView allChip;
    private ChipView eventChip;
    private ChipView pointsChip;
    private ChipView newsChip;
    private ChipView paperChip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chips);
        Bundle bundle = getIntent().getExtras();
        allChip = findViewById(R.id.chip_all);
        eventChip = findViewById(R.id.chip_event);
        pointsChip = findViewById(R.id.chip_points);
        newsChip = findViewById(R.id.chip_news);
        paperChip = findViewById(R.id.chip_paper);
        allChip.setChecked(bundle.getBoolean("All"));
        eventChip.setChecked(bundle.getBoolean("Event"));
        pointsChip.setChecked(bundle.getBoolean("Points"));
        newsChip.setChecked(bundle.getBoolean("News"));
        paperChip.setChecked(bundle.getBoolean("Paper"));
        if(!allChip.isChecked()) {
            allChip.startShakeAnimation(0.8f, 1.1f, 0.3f,100);
        }
        if(!eventChip.isChecked()) {
            eventChip.startShakeAnimation(0.8f, 1.1f, 0.3f,100);
        }
        if(!pointsChip.isChecked()) {
            pointsChip.startShakeAnimation(0.9f, 1.1f, 0.3f,100);
        }
        if(!newsChip.isChecked()) {
            newsChip.startShakeAnimation(0.9f, 1.1f, 0.3f,100);
        }
        if(!paperChip.isChecked()) {
            paperChip.startShakeAnimation(0.9f, 1.1f, 0.3f,100);
        }
        allChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!allChip.isChecked()) {
                    allChip.startShakeAnimation(0.8f, 1.1f, 0.3f,100);
                }
            }
        });
        eventChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!eventChip.isChecked()) {
                    eventChip.startShakeAnimation(0.8f, 1.1f, 0.3f,100);
                }
            }
        });
        pointsChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pointsChip.isChecked()) {
                    pointsChip.startShakeAnimation(0.9f, 1.1f, 0.3f,100);
                }
            }
        });
        newsChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!newsChip.isChecked()) {
                    newsChip.startShakeAnimation(0.9f, 1.1f, 0.3f,100);
                }
            }
        });
        paperChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!paperChip.isChecked()) {
                    paperChip.startShakeAnimation(0.9f, 1.1f, 0.3f,100);
                }
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("All", allChip.isChecked());
        bundle.putBoolean("Event", eventChip.isChecked());
        bundle.putBoolean("Points", pointsChip.isChecked());
        bundle.putBoolean("News", newsChip.isChecked());
        bundle.putBoolean("Paper", paperChip.isChecked());
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(2,intent);
        super.onBackPressed();
    }
}

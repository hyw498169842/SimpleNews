package com.java.huangyuwei;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;

public class ChipsActivity extends AppCompatActivity {
    private Chip allChip;
    private Chip eventChip;
    private Chip pointsChip;
    private Chip newsChip;
    private Chip paperChip;
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

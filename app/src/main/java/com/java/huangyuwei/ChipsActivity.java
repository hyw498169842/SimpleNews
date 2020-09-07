package com.java.huangyuwei;

import android.content.Intent;
import android.os.Bundle;

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
        allChip.setChecked(bundle.getBoolean("all"));
        eventChip.setChecked(bundle.getBoolean("event"));
        pointsChip.setChecked(bundle.getBoolean("points"));
        newsChip.setChecked(bundle.getBoolean("news"));
        paperChip.setChecked(bundle.getBoolean("paper"));
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("all", allChip.isChecked());
        bundle.putBoolean("event", eventChip.isChecked());
        bundle.putBoolean("points", pointsChip.isChecked());
        bundle.putBoolean("news", newsChip.isChecked());
        bundle.putBoolean("paper", paperChip.isChecked());
        Intent intent = new Intent();
        super.onBackPressed();
    }
}

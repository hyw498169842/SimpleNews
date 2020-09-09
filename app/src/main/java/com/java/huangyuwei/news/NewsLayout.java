package com.java.huangyuwei.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.java.huangyuwei.NewsDetailActivity;
import com.java.huangyuwei.R;
import com.java.huangyuwei.news.newssaver.News;
import com.java.huangyuwei.news.newssaver.News2Json;

public class NewsLayout extends LinearLayout {
    public TextView contentView;
    @SuppressLint("ResourceType")
    public NewsLayout(final Context context, final String titleString, String textString, final String date, final String type, final String sourceString) {
        super(context);

        //System.out.println(textString);
        setOrientation(LinearLayout.VERTICAL);
        TextView title = new TextView(context);
        title.setText(titleString);
        title.setTextColor(Color.rgb(0x72,0x70,0x7b));
        title.setTextSize(15);
        title.getPaint().setFakeBoldText(true);
        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.setMargins(45,25,45,0);
        title.setLayoutParams(titleParams);
        addView(title);

        RelativeLayout infoLayout = new RelativeLayout(context);
        LayoutParams infoParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        infoParams.setMargins(35,15,35,25);
        infoLayout.setBackgroundResource(R.drawable.rectangle);
        infoLayout.setLayoutParams(infoParams);
        final String finalTextString = textString;

        contentView = new TextView(context);
        if(textString.equals("")) {
            if(type.equals("event")) {
                textString = "Attend this event!";
            }
            else if(type.equals("points")) {
                textString = "Check this point!";
            }
        }
        if(textString.length() > 50) {
            textString = textString.substring(0, 50) + "...";
        }
        contentView.setText(textString);
        LayoutParams contentParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentView.setTextColor(Color.rgb(0x6e,0x9c,0xf9));
        contentParams.setMargins(15,10,15,10);
        contentView.setLayoutParams(contentParams);
        infoLayout.addView(contentView);
        contentView.setId(1);

        TextView dateView = new TextView(context);
        dateView.setText(date);
        RelativeLayout.LayoutParams dateParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dateParams.addRule(RelativeLayout.BELOW, 1);
        dateView.setTextColor(Color.rgb(0x6e,0x9c,0xf9));
        //dateParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        dateParams.setMargins(15,10,15,10);
        dateView.setLayoutParams(dateParams);
        infoLayout.addView(dateView);

        TextView typeView = new TextView(context);
        typeView.setText(type);
        if(type.equals("news")) {
            typeView.setText(sourceString);
        }
        RelativeLayout.LayoutParams typeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        typeParams.addRule(RelativeLayout.BELOW, 1);
        typeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        typeView.setTextColor(Color.rgb(180,180,180));

        typeParams.setMargins(0,10,35,10);
        typeView.setLayoutParams(typeParams);
        infoLayout.addView(typeView);

        addView(infoLayout);

        final NewsLayout _this = this;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // NewsDetailActivity
                if(type.equals("news") || type.equals("paper")) {
                    _this.setBackgroundColor(Color.rgb(0xe0,0xe0,0xe0));
                    News news = new News();
                    news.setTitle(titleString);
                    news.setContent(finalTextString);
                    news.setDate(date);
                    news.setSource(sourceString);
                    News2Json.addNews(context.getFilesDir().getPath(), news);
                    Intent intent = new Intent(context, NewsDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("title", titleString);
                    bundle.putCharSequence("content", finalTextString);
                    bundle.putCharSequence("date", date);
                    bundle.putCharSequence("type", type);
                    bundle.putCharSequence("source", sourceString);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else if("You like this!".contentEquals(contentView.getText())) {
                    setBackgroundColor(Color.rgb(255,255,255));
                    if(type.equals("event")) {
                        contentView.setText("Attend this event!");
                    } else if(type.equals("points")) {
                        contentView.setText("Check this point!");
                    }
                } else {
                    setBackgroundColor(Color.rgb(0xFF, 192, 203));
                    contentView.setText("You like this!");
                }
            }
        });

        setBackgroundColor(Color.rgb(255,255,255));
    }
}

package com.java.huangyuwei;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.java.huangyuwei.covid.bean.ResearcherItemBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class ResearcherDetailActivity extends AppCompatActivity {

	private void packIntroduction(LinearLayout layout, String title, Spanned content) {
		if(!"".contentEquals(content)) {
			// 标题
			TextView start = new TextView(this);
			start.setText(title);
			start.setTextSize(20);
			start.setTextColor(Color.DKGRAY);
			start.getPaint().setFakeBoldText(true);
			LinearLayout.LayoutParams startParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			startParams.setMargins(30, 50, 30, 0);
			start.setLayoutParams(startParams);
			layout.addView(start);
			// 正文
			TextView text = new TextView(this);
			text.setText(content);
			text.setTextSize(16);
			text.setLineSpacing(0, 1.5f);
			text.setTextColor(Color.rgb(128, 128, 255));
			LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			textParams.setMargins(80, 40, 80, 10);
			text.setLayoutParams(textParams);
			layout.addView(text);
		}
	}

	private void packGrid(LinearLayout layout, JSONObject object, String title, boolean isTag) throws JSONException {
		if(object.names() != null) {
			// 标题
			TextView start = new TextView(this);
			start.setText(title);
			start.setTextSize(20);
			start.setTextColor(Color.DKGRAY);
			start.getPaint().setFakeBoldText(true);
			LinearLayout.LayoutParams startParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			startParams.setMargins(30, 50, 30, 0);
			start.setLayoutParams(startParams);
			layout.addView(start);
			// 表格
			JSONArray names = object.names();
			GridLayout gridLayout = new GridLayout(this);
			for(int i = 0; i < names.length(); i++) {
				String name = names.getString(i);
				double value = object.getDouble(name);
				// 属性名
				TextView nameText = new TextView(this);
				nameText.setMaxEms(8);
				nameText.setText(name);
				nameText.setTextSize(16);
				nameText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
				nameText.setTextColor(Color.rgb(0xA7, 0xA7, 0xA7));
				nameText.getPaint().setFakeBoldText(true);
				GridLayout.LayoutParams nameParams = new GridLayout.LayoutParams(
					GridLayout.spec(i, GridLayout.CENTER), GridLayout.spec(0, GridLayout.CENTER, 1));
				nameParams.setMargins(0, 20, 0, 30);
				nameText.setLayoutParams(nameParams);
				gridLayout.addView(nameText);
				// 属性值
				TextView valueText = new TextView(this);
				valueText.setText(String.format(Locale.CHINA, "%s%s", isTag ? "得分：" : "", value));
				valueText.setTextSize(16);
				valueText.setTextColor(Color.rgb(0x80, 0x80, 0xFF));
				GridLayout.LayoutParams valueParams = new GridLayout.LayoutParams(
					GridLayout.spec(i, GridLayout.CENTER), GridLayout.spec(1, GridLayout.CENTER, 1));
				valueParams.setMargins(0, 20, 0, 30);
				valueText.setLayoutParams(valueParams);
				gridLayout.addView(valueText);
			}
			// 加入布局
			LinearLayout.LayoutParams gridLayoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			gridLayoutParams.setMargins(30, 20, 30, 0);
			gridLayout.setLayoutParams(gridLayoutParams);
			layout.addView(gridLayout);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_researcher_detail);

		Toolbar toolbar = findViewById(R.id.toolbar_r);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		try {
			ResearcherItemBean item = ResearcherItemBean.getInstance();
			String name = item.getName();
			String affiliation = item.getAffiliation();
			String position = item.getPosition();
			String email = item.getEmail();
			String iconUrl =item.getIconUrl();
			String bio = item.getBio();
			String edu = item.getEdu();
			String work = item.getWork();
			JSONObject indices = new JSONObject(item.getIndices());
			JSONObject tags = new JSONObject(item.getTags());

			toolbar.setTitle(name);
			// 获取全局layout
			LinearLayout layout = findViewById(R.id.researcher_detail_layout);
			// 姓名文本框
			TextView nameText = new TextView(this);
			nameText.setText(name);
			nameText.setTextSize(20);
			nameText.setTextColor(Color.DKGRAY);
			nameText.getPaint().setFakeBoldText(true);
			// 单位文本框
			TextView affiliationText = new TextView(this);
			affiliationText.setText(affiliation);
			affiliationText.setTextSize(15);
			affiliationText.setTextColor(Color.rgb(128, 128, 255));
			// 职位文本框
			TextView positionText = new TextView(this);
			positionText.setText(position);
			positionText.setTextSize(15);
			positionText.setTextColor(Color.rgb(128, 128, 255));
			// 电子邮件文本框
			TextView emailText = new TextView(this);
			emailText.setText(email);
			emailText.setTextSize(15);
			emailText.setTextColor(Color.DKGRAY);
			// 设置layout参数
			LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			infoParams.setMargins(15, 15, 15, 15);
			nameText.setLayoutParams(infoParams);
			affiliationText.setLayoutParams(infoParams);
			positionText.setLayoutParams(infoParams);
			emailText.setLayoutParams(infoParams);
			// 以上文本框纵向布局
			LinearLayout infoLayout = new LinearLayout(this);
			infoLayout.setOrientation(LinearLayout.VERTICAL);
			infoLayout.addView(nameText);
			infoLayout.addView(affiliationText);
			infoLayout.addView(positionText);
			infoLayout.addView(emailText);
			// 学者头像
			ImageView icon = new ImageView(this);
			RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
			Glide.with(this).load(iconUrl).apply(options).into(icon);
			icon.setAdjustViewBounds(true);
			icon.setMaxWidth(300);
			// 头像与文本框横向布局
			LinearLayout.LayoutParams fullInfoParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
			fullInfoParams.setMargins(10, 10, 10, 10);
			icon.setLayoutParams(fullInfoParams);
			infoLayout.setLayoutParams(fullInfoParams);
			LinearLayout infoWrapper = new LinearLayout(this);
			infoWrapper.addView(icon);
			infoWrapper.addView(infoLayout);
			layout.addView(infoWrapper);
			// 各学术指标
			packGrid(layout, indices, "学术指标", false);
			// 人物生平
			packIntroduction(layout, "人物生平", Html.fromHtml(bio, Html.FROM_HTML_MODE_COMPACT));
			// 教育经历
			packIntroduction(layout, "教育经历", new SpannableString(edu));
			// 工作经历
			packIntroduction(layout, "工作经历", new SpannableString(work));
			// 人物标签
			packGrid(layout, tags, "人物标签", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
package com.java.huangyuwei.covid;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import lecho.lib.hellocharts.view.LineChartView;
import com.java.huangyuwei.R;
import com.java.huangyuwei.covid.util.ChartPainter;
import com.java.huangyuwei.covid.util.CovidDataJsonParser;
import com.java.huangyuwei.covid.util.UrlContentReader;

public class CovidDataFragment extends Fragment {
	private Handler handler;
	private View savedView;
	private Activity savedActivity;

	private OptionsPickerView<String> options;
	private List<String> optionCountries = new ArrayList<>();
	private List<List<String>> optionProvinces = new ArrayList<>();
	private List<List<List<String>>> optionCounties = new ArrayList<>();

	private HashMap<String, Date> beginDate = new HashMap<>();
	private HashMap<String, ArrayList<ArrayList<Integer>>> covidData = new HashMap<>();

	private int type;
	private Date startDate;
	private Date finishDate;
	private String currentKey;
	private boolean chartInitialized;
	private final String url = "https://covid-dashboard.aminer.cn/api/dist/epidemic.json";

	private LoadingDialog dialog;
	private EditText editStart;
	private EditText editFinish;

	private void setStartDate(Date date) {
		if(editStart == null)
			editStart = savedView.findViewById(R.id.edit_start);
		editStart.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(startDate = date));
	}

	private void setFinishDate(Date date) {
		if(editFinish == null)
			editFinish = savedView.findViewById(R.id.edit_finish);
		editFinish.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(finishDate = date));
	}

	private void setCurrentLocation(int op1, int op2, int op3) {
		String country = optionCountries.get(op1);
		String province = optionProvinces.get(op1).get(op2);
		String county = optionCounties.get(op1).get(op2).get(op3);
		currentKey = String.join("|", country, province, county);
		((TextView) savedView.findViewById(R.id.text_country)).setText(String.format(getString(R.string.current_country), country));
		((TextView) savedView.findViewById(R.id.text_province)).setText(String.format(getString(R.string.current_province), province));
		((TextView) savedView.findViewById(R.id.text_county)).setText(String.format(getString(R.string.current_county), county));
		setStartDate(beginDate.get(currentKey));
		long dataLength = Objects.requireNonNull(covidData.get(currentKey)).size();
		setFinishDate(new Date(startDate.getTime() + dataLength * ChartPainter.MILLISECOND_PER_DAY));
	}

	private void drawChartOfCurrentState() {
		try {
			chartInitialized = true;
			LineChartView chart = savedView.findViewById(R.id.line_chart);
			ChartPainter.drawChart(chart, Objects.requireNonNull(covidData.get(currentKey)),
				Objects.requireNonNull(beginDate.get(currentKey)), startDate, finishDate, type);
		} catch (NullPointerException e) {
			chartInitialized = false;
		}
	}

	private Date checkBound(Date date) {
		if(date != null) {
			long downLimit = Objects.requireNonNull(beginDate.get(currentKey)).getTime();
			if(date.getTime() < downLimit) return new Date(downLimit);
			long dataLength = Objects.requireNonNull(covidData.get(currentKey)).size();
			long upLimit = downLimit + dataLength * ChartPainter.MILLISECOND_PER_DAY;
			if(date.getTime() > upLimit) return new Date(upLimit);
		}
		return date;
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		savedActivity = (Activity)context;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(savedView != null) {
			if(!chartInitialized) {
				setCurrentLocation(0, 0, 0);
				drawChartOfCurrentState();
			}
			return savedView;
		}

		savedView = inflater.inflate(R.layout.covid_data_fragment, container, false);
		// show loading dialog
		dialog = new LoadingDialog(savedActivity);
		dialog.setLoadingText("加载疫情数据中，请稍候").show();
		// use handler to handle callback message
		handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(@NonNull Message message) {
				if(message.what == 0) {
					savedView = null;
					dialog.setFailedText("加载失败，请检查网络").loadFailed();
				} else if(message.what == 1) {
					options.setPicker(optionCountries, optionProvinces, optionCounties);
					setCurrentLocation(0, 0, 0);
					drawChartOfCurrentState();
					dialog.close();
				} else if(message.what == 2) {
					drawChartOfCurrentState();
				}
				return true;
			}
		});
		// get url content and parse it
		new Thread() {
			@Override
			public void run() {
				try {
					CovidDataJsonParser.parse(UrlContentReader.getContent(url), optionCountries,
						optionProvinces, optionCounties, beginDate, covidData);
					handler.sendEmptyMessage(1);
				} catch (IOException | JSONException | ParseException | NullPointerException e) {
					handler.sendEmptyMessage(0);
				}
			}
		}.start();
		// initialize the OptionsPickerView
		options = new OptionsPickerBuilder(savedActivity, new OnOptionsSelectListener() {
			@Override
			public void onOptionsSelect(int op1, int op2, int op3, View v) {
				if(optionCountries.isEmpty()) return;
				setCurrentLocation(op1, op2, op3);
				drawChartOfCurrentState();
			}
		}).build();
		options.setTitleText("请选择地区");
		// initialize the button
		Button button_choose = savedView.findViewById(R.id.button_choose);
		button_choose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				options.show();
			}
		});
		// initialize the edit text
		editStart = savedView.findViewById(R.id.edit_start);
		editFinish = savedView.findViewById(R.id.edit_finish);
		editStart.setInputType(InputType.TYPE_NULL);
		editFinish.setInputType(InputType.TYPE_NULL);
		final Calendar calendar = Calendar.getInstance();
		editStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(startDate != null) calendar.setTime(startDate);
				new DatePickerDialog(savedActivity, new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker datePicker, int y, int M, int d) {
						try {
							String formattedDate = y + "-" + (M + 1) + "-" + d;
							setStartDate(checkBound(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(formattedDate)));
							handler.sendEmptyMessage(2);
						} catch (ParseException | NullPointerException e) {
							e.printStackTrace();
						}
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		editFinish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(finishDate != null) calendar.setTime(finishDate);
				new DatePickerDialog(savedActivity, new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker datePicker, int y, int M, int d) {
						try {
							String formattedDate = y + "-" + (M + 1) + "-" + d;
							setFinishDate(checkBound(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(formattedDate)));
							handler.sendEmptyMessage(2);
						} catch (ParseException | NullPointerException e) {
							e.printStackTrace();
						}
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		// initialize the spinner
		Spinner spinner = savedView.findViewById(R.id.spinner);
		ArrayList<String> items = new ArrayList<>(Arrays.asList("累计确诊", "疑似病例", "累计治愈", "累计死亡"));
		spinner.setAdapter(new ArrayAdapter<>(savedActivity, android.R.layout.simple_spinner_dropdown_item, items));
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				type = i; handler.sendEmptyMessage(2);
			}
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {}
		});
		return savedView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(savedView != null && savedView.getParent() != null) {
			((ViewGroup)savedView.getParent()).removeView(savedView);
		}
	}
}

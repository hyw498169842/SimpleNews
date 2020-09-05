package com.java.huangyuwei.covid.util;

import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class ChartPainter {
	public static final long MILLISECOND_PER_DAY = 24 * 60 * 60 * 1000;

	public static void drawChart(LineChartView chart,ArrayList<ArrayList<Integer>> data, Date dataBegin, Date begin, Date end, int type) {
		ArrayList<AxisValue> xValues = new ArrayList<>();
		ArrayList<PointValue> values = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		long startIndex = (begin.getTime() - dataBegin.getTime()) / MILLISECOND_PER_DAY;
		for(int i = (int)startIndex; i < data.size(); i++) {
			long currentTime = dataBegin.getTime() + (long)i * MILLISECOND_PER_DAY;
			if(currentTime >= end.getTime()) break;
			if(data.get(i).get(type) != null) {
				values.add(new PointValue(i, data.get(i).get(type)));
				xValues.add(new AxisValue(i).setLabel(format.format(new Date(currentTime))));
			}
		}
		ArrayList<Line> lines = new ArrayList<>();
		lines.add(new Line(values).setColor(Color.GRAY).setPointRadius(3).setHasLabelsOnlyForSelected(true));
		LineChartData chart_data = new LineChartData(lines);
		chart_data.setAxisXBottom(new Axis(xValues).setMaxLabelChars(10).setTextColor(Color.BLACK).setLineColor(Color.BLACK));
		chart_data.setAxisYLeft(new Axis().setMaxLabelChars(8).setTextColor(Color.BLACK).setLineColor(Color.BLACK));
		chart.setLineChartData(chart_data);
	}
}

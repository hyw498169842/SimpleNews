package com.java.huangyuwei.covid.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CovidDataJsonParser {
	public static void parse(
		String json,
		List<String> countries,
		List<List<String>> provinces,
		List<List<List<String>>> counties,
		HashMap<String, Date> beginDate,
		HashMap<String, ArrayList<ArrayList<Integer>>> covidData
	) throws JSONException, ParseException, NullPointerException {
		HashMap<String, HashSet<String>> provincesMap = new HashMap<>();
		HashMap<String, HashSet<String>> countiesMap = new HashMap<>();

		JSONObject jsonObject = new JSONObject(json);
		for(Iterator<String> iter = jsonObject.keys(); iter.hasNext(); ) {
			// build relation among countries, provinces, and counties
			String location = iter.next();
			ArrayList<String> parts = new ArrayList<>(Arrays.asList(location.split("\\|")));
			while(parts.size() < 3) parts.add("None");
			if(!provincesMap.containsKey(parts.get(0)))
				provincesMap.put(parts.get(0), new HashSet<String>());
			Objects.requireNonNull(provincesMap.get(parts.get(0))).add(parts.get(1));
			if(!countiesMap.containsKey(parts.get(1)))
				countiesMap.put(parts.get(1), new HashSet<String>());
			Objects.requireNonNull(countiesMap.get(parts.get(1))).add(parts.get(2));
			// put begin date into map
			String key = String.join("|", parts);
			JSONObject locationData = jsonObject.getJSONObject(location);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
			beginDate.put(key, format.parse(locationData.getString("begin")));
			// put covid data into map
			covidData.put(key, new ArrayList<ArrayList<Integer>>());
			JSONArray dataArray = locationData.getJSONArray("data");
			for(int i = 0; i < dataArray.length(); i++) {
				JSONArray subArray = dataArray.getJSONArray(i);
				ArrayList<Integer> intArray = new ArrayList<>();
				for(int j = 0; j < subArray.length(); j++) {
					intArray.add(subArray.isNull(j) ? null : subArray.getInt(j));
				}
				Objects.requireNonNull(covidData.get(key)).add(intArray);
			}
		}
		// initialize the option list
		countries.addAll(provincesMap.keySet());
		Collections.sort(countries);
		for(String country: countries) {
			ArrayList<String> itsProvinces = new ArrayList<>(Objects.requireNonNull(provincesMap.get(country)));
			Collections.sort(itsProvinces);
			provinces.add(itsProvinces);
			ArrayList<List<String>> countiesOfEach = new ArrayList<>();
			for(String province: itsProvinces) {
				ArrayList<String> itsCounties = new ArrayList<>(Objects.requireNonNull(countiesMap.get(province)));
				Collections.sort(itsCounties);
				countiesOfEach.add(itsCounties);
			}
			counties.add(countiesOfEach);
		}
	}
}

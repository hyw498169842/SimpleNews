package com.java.huangyuwei.covid.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlContentReader {
	public static String getContent(String url) throws IOException {
		InputStream stream = new URL(url).openConnection().getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder stringBuilder = new StringBuilder();
		for(String buff = reader.readLine(); buff != null; ) {
			stringBuilder.append(buff);
			stringBuilder.append("\n");
			buff = reader.readLine();
		}
		return stringBuilder.toString();
	}
}

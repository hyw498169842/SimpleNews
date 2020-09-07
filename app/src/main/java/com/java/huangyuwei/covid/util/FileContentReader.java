package com.java.huangyuwei.covid.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileContentReader {
	public static String getContent(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder builder = new StringBuilder();
		for(String buffer = reader.readLine(); buffer != null; ) {
			builder.append(buffer);
			builder.append("\n");
			buffer = reader.readLine();
		}
		return builder.toString();
	}
}

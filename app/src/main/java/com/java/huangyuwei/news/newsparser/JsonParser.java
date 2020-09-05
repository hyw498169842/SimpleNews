package com.java.huangyuwei.news.newsparser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

class JsonParser {
    final int N = 8;
    private BufferedReader bReader;
    String[][] getNNews(int pageNum, String type){
        String[][] nextNNews = new String[N][5];
        try {
            URL titleURL = new URL("https://covid-dashboard.aminer.cn/api/events/list?size=" + N + "&page=" + pageNum + "&type=" + type);
            InputStream ins = titleURL.openStream();
            InputStreamReader reader = new InputStreamReader(ins, "UTF-8");
            bReader = new BufferedReader(reader);
            String tmp;
            String jsonString = new String();
            while((tmp = bReader.readLine()) != null) {
                jsonString = jsonString + tmp;
            }
            JSONObject t = (JSONObject)JSON.parse(jsonString);
            JSONArray newsJsonArray = (JSONArray)t.get("data");
            for(int i = 0; i < N; i++) {
                JSONObject news = (JSONObject)newsJsonArray.get(i);
                nextNNews[i][0] = (String)news.get("title");
                nextNNews[i][1] = (String)news.get("content");
                nextNNews[i][2] = (String)news.get("date");
                nextNNews[i][3] = (String)news.get("type");
                nextNNews[i][4] = (String)news.get("source");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nextNNews;
    }
    JsonParser() {


    }
}

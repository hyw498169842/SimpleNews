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
import java.nio.charset.StandardCharsets;

class JsonParser {
    final int N = 8;
    private BufferedReader bReader;
    String[][] getKeyNews(final String keyWord, final int newsNum, final int maxPageNum, final int numPerPage){
        String[][] nextNNews = new String[newsNum][5];
        int num = 0;
        try {
            for(int pageNum = 1; pageNum < maxPageNum; pageNum++) {
                URL titleURL = new URL("https://covid-dashboard.aminer.cn/api/events/list?size=" + numPerPage + "&page=" + pageNum + "&type=news");
                InputStream ins = titleURL.openStream();
                InputStreamReader reader = new InputStreamReader(ins, StandardCharsets.UTF_8);
                bReader = new BufferedReader(reader);
                String tmp;
                StringBuilder jsonString = new StringBuilder();
                while ((tmp = bReader.readLine()) != null) {
                    jsonString.append(tmp);
                }
                JSONObject t = (JSONObject) JSON.parse(jsonString.toString());
                JSONArray newsJsonArray = t.getJSONArray("data");
                for (int i = 0; i < newsJsonArray.size(); i++) {
                    JSONObject news = newsJsonArray.getJSONObject(i);
                    String title = (String) news.getString("title");
                    if(title.contains(keyWord)) {
                        nextNNews[num][0] = news.getString("title");
                        nextNNews[num][1] = news.getString("content");
                        nextNNews[num][2] = news.getString("date");
                        nextNNews[num][3] = news.getString("type");
                        nextNNews[num++][4] = news.getString("source");
                    }
                    if(num == newsNum)  return  nextNNews;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nextNNews;
    }
    String[][] getNNews(int pageNum, String type) throws IOException {
        String[][] nextNNews = new String[N][5];
        try {
            URL titleURL = new URL("https://covid-dashboard.aminer.cn/api/events/list?size=" + N + "&page=" + pageNum + "&type=" + type);
            InputStream ins = titleURL.openStream();
            InputStreamReader reader = new InputStreamReader(ins, StandardCharsets.UTF_8);
            bReader = new BufferedReader(reader);
            String tmp;
            StringBuilder jsonString = new StringBuilder();
            while((tmp = bReader.readLine()) != null) {
                jsonString.append(tmp);
            }
            JSONObject t = (JSONObject)JSON.parse(jsonString.toString());
            JSONArray newsJsonArray = t.getJSONArray("data");
            for(int i = 0; i < N; i++) {
                JSONObject news = newsJsonArray.getJSONObject(i);
                nextNNews[i][0] = news.getString("title");
                nextNNews[i][1] = news.getString("content");
                nextNNews[i][2] = news.getString("date");
                nextNNews[i][3] = news.getString("type");
                nextNNews[i][4] = news.getString("source");
            }

        } catch (MalformedURLException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return nextNNews;
    }
    JsonParser() {


    }
}

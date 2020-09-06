package com.java.huangyuwei.news.newssaver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class News2Json {
    public static String[][] getNewsList(String filePath) {
        String jsonString = readJson(filePath);
        JSONArray newsArray = (JSONArray)JSON.parse(jsonString);
        if(newsArray == null) {
            return null;
        }
        String[][] newsList = new String[newsArray.size()][5];
        for(int i = 0; i < newsArray.size(); i++) {
            newsList[i][0] = ((JSONObject)newsArray.get(i)).getString("title");
            newsList[i][1] = ((JSONObject)newsArray.get(i)).getString("content");
            newsList[i][2] = ((JSONObject)newsArray.get(i)).getString("date");
            newsList[i][3] = "news";
            newsList[i][4] = ((JSONObject)newsArray.get(i)).getString("source");
        }
        return newsList;
    }
    static String readJson(String filePath) {
        String content = new String();
        String fileString = filePath + "/simpnews.json";
        File file = new File(fileString);
        if(file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String tmp = new String();
                content = bufferedReader.readLine();
                while((tmp = bufferedReader.readLine()) != null) {
                    content = content + tmp;
                }
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(content == null) {
            content = new String();
        }
        return content;
    }

    public static void addNews(String filePath, News news) {
        String previous = readJson(filePath);
        String newString = new String();
        if(!previous.equals("")) {
            newString = previous.substring(0, previous.length() - 1) + "," + JSON.toJSONString(news) + "]";
        }
        else {
            newString = "[" + JSON.toJSONString(news) + "]";
        }
        System.out.println(newString);
        save(filePath, newString);
    }

    static void save(String filePath, String saveString) {
        String fileString = filePath + "/simpnews.json";
        File file = new File(fileString);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(saveString);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String filePath) {
        String fileString = filePath + "/simpnews.json";
        File file = new File(fileString);
        if(file.exists())
            file.delete();
    }
}

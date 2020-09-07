package com.java.huangyuwei.news.newsparser;

import android.os.Handler;
import android.os.Message;

public class Searcher extends Thread {
    private Handler mainThreadHandler;
    private String keyWord;
    @Override
    public void run() {
        super.run();
        JsonParser jsonParser = new JsonParser();
        String[][] newsList = jsonParser.getKeyNews(keyWord, 8, 15,100);
        int len = 0;
        for(; len < newsList.length; len++) {
            if(newsList[len][0] == null) {
                break;
            }
        }
        String[][] listNews = new String[len][5];
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < 5; j++) {
                listNews[i][j] = newsList[i][j];
            }
        }
        Message msg = new Message();
        msg.obj = listNews;
        msg.what = 1;
        mainThreadHandler.sendMessage(msg);
    }
    public Searcher(Handler handler, String string) {
        mainThreadHandler = handler;
        keyWord = string;
    }
}

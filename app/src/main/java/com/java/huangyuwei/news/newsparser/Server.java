package com.java.huangyuwei.news.newsparser;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

public class Server extends Thread {
    static int page = 0;
    protected String type;
    protected Handler mainThreadHandler;
    @Override
    public void run() {
        super.run();
        JsonParser jParser = new JsonParser();
        page++;
        String[][] newsList = new String[0][];
        try {
            newsList = jParser.getNNews(page, type);
        } catch (IOException e) {
            Message msg = new Message();
            msg.what = 2;
            mainThreadHandler.sendMessage(msg);
            return;
        }
        Message message = new Message();
        message.obj = newsList;
        message.what = 0;
        mainThreadHandler.sendMessage(message);
    }
    public Server(Handler mHandler, String _type) {
        mainThreadHandler = mHandler;
        type = _type;
    }
}


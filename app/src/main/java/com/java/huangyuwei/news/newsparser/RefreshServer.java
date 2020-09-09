package com.java.huangyuwei.news.newsparser;


import android.os.Handler;
import android.os.Message;

import java.io.IOException;

public class RefreshServer extends Server {
    @Override
    public void run() {
        super.run();
        JsonParser jParser = new JsonParser();
        String[][] newsList = new String[0][];
        try {
            newsList = jParser.getNNews(1, super.type);
        } catch (IOException e) {
            Message msg = new Message();
            msg.what = 2;
            mainThreadHandler.sendMessage(msg);
            return;
        }
        Message message = new Message();
        message.obj = newsList;
        message.what = 1;
        super.mainThreadHandler.sendMessage(message);
    }
    public RefreshServer(Handler mHandler, String _type) {
        super(mHandler, _type, 1);
    }
}
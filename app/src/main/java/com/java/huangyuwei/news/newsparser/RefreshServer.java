package com.java.huangyuwei.news.newsparser;


import android.os.Handler;
import android.os.Message;

public class RefreshServer extends Server {
    @Override
    public void run() {
        super.run();
        JsonParser jParser = new JsonParser();
        page++;
        String[][] newsList = jParser.getNNews(1, super.type);
        Message message = new Message();
        message.obj = newsList;
        message.what = 1;
        super.mainThreadHandler.sendMessage(message);
    }
    public RefreshServer(Handler mHandler, String _type) {
        super(mHandler, _type);
    }
}
package com.java.huangyuwei.news.newssaver;

import com.alibaba.fastjson.annotation.JSONField;

public class News {
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "content")
    private String content;
    @JSONField(name = "date")
    private String date;
    @JSONField(name = "source")
    private String source;
    public News() {
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

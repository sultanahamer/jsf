package com.openapp.jsf.domain;

import com.openapp.jsf.database.Items;

import org.json.JSONException;
import org.json.JSONObject;

public class News extends Item {
    private String content;

    public News(JSONObject news, boolean read, String timestamp) throws JSONException {
        super(news.getString("title"), read, timestamp, Items.TYPE_NEWS);
        this.content = news.getString("content");
    }

    public String getContent() {
        return content;
    }
}

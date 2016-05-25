package com.openapp.jsf.domain;

import java.io.Serializable;

public class Item implements Serializable{
    protected String title;
    protected boolean read;
    protected String timestamp;
    protected int type;
    public Item(String title, boolean read, String timestamp, int type) {
        this.title = title;
        this.read = read;
        this.timestamp = timestamp;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return title;
    }

    public void read(){
        read = true;
    }

    public void unread(){
        read = false;
    }
    public boolean isRead(){
        return read;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public String getTitle(){
        return title;
    }
}

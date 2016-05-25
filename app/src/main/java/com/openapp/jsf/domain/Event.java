package com.openapp.jsf.domain;

import com.openapp.jsf.database.Items;
import com.openapp.jsf.widget.tab.TabManager;

import org.json.JSONException;
import org.json.JSONObject;

public class Event extends Item {
    private String venue;
    private String datetime;
    private String description;

    public Event(JSONObject event, boolean read, String timestamp) throws JSONException {
        super(event.getString("title"), read, timestamp, Items.TYPE_EVENT);
        this.venue = event.getString("venue");
        this.datetime = event.getString("datetime");
        this.description = event.getString("description");
    }


    public String getVenue() {
        return venue;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getDescription() {
        return description;
    }
}

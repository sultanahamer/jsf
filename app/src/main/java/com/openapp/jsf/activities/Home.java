package com.openapp.jsf.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.openapp.jsf.database.Items;
import com.openapp.jsf.domain.JobPost;
import com.openapp.jsf.services.Updates;
import com.openapp.jsf.widget.tab.TabContentClickHandler;
import com.openapp.jsf.widget.tab.TabManager;

import java.util.ArrayList;

public class Home extends Activity {

    ArrayList<JobPost> jobPosts;
    ArrayList<com.openapp.jsf.domain.Event> events;
    ArrayList<com.openapp.jsf.domain.News> news;
    private Items itemsHelper;
    TabManager tabManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(TabManager.layout);
        tabManager = new TabManager(this);
        startDatabase();
        startService();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initalizeApp();
    }

    private void startDatabase() {
        itemsHelper = new Items(this);
        SQLiteDatabase db = itemsHelper.getWritableDatabase();
        itemsHelper.setDB(db);
    }

    private void startService() {
        Intent serviceIntent = new Intent(this, Updates.class);
        startService(serviceIntent);
    }

    public Items getItemsHelper(){
        return itemsHelper;
    }



    private void initalizeApp() {
        updateItems();
        tabManager.highlightDefaultTab();
    }

    private void updateItems() {
        ArrayList[] items = itemsHelper.getItems();
        jobPosts = items[Items.TYPE_JOB];
        events = items[Items.TYPE_EVENT];
        news = items[Items.TYPE_NEWS]   ;
        addItems();
    }

    private void addItems() {

        final Activity context = this;

        TabContentClickHandler onItemClicked = new TabContentClickHandler() {
            @Override
            public void onClick(View view, int index, int tabIndex) {
                Intent intent = new Intent();
                switch(tabIndex){
                    case TabManager.TAB_JOBS:
                        intent.setClass(context, com.openapp.jsf.activities.JobPost.class);
                        intent.putExtra(com.openapp.jsf.activities.JobPost.EXTRA_JOBPOST, jobPosts.get(index));
                        itemsHelper.setRead(jobPosts.get(index));
                        break;

                    case TabManager.TAB_EVENTS:
                        intent.setClass(context, com.openapp.jsf.activities.Event.class);
                        intent.putExtra(Event.EXTRA_JOBPOST, events.get(index));
                        itemsHelper.setRead(events.get(index));
                        break;
                    case TabManager.TAB_NEWS:
                        intent.setClass(context, com.openapp.jsf.activities.News.class);
                        intent.putExtra(News.EXTRA_JOBPOST, news.get(index));
                        itemsHelper.setRead(news.get(index));
                        break;
                }

                startActivity(intent);
            }
        };
        tabManager.setOnItemClicked(onItemClicked);
        tabManager.getTab(TabManager.TAB_JOBS).setContent(jobPosts);
        tabManager.getTab(TabManager.TAB_EVENTS).setContent(events);
        tabManager.getTab(TabManager.TAB_NEWS).setContent(news);
    }

}

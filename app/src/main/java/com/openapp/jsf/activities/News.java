package com.openapp.jsf.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.openapp.jsf.activities.R;

public class News extends ActionBarActivity {

    public static final String EXTRA_JOBPOST = "NEWS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        com.openapp.jsf.domain.News news = (com.openapp.jsf.domain.News) intent.getSerializableExtra(EXTRA_JOBPOST);
        ((TextView)findViewById(R.id.title)).setText(news.getTitle());
        ((TextView)findViewById(R.id.content)).setText(news.getContent());
    }

}

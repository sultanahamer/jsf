package com.openapp.jsf.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

public class Event extends android.support.v7.app.AppCompatActivity {

    public static final String EXTRA_JOBPOST = "EVENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Intent intent = getIntent();
        com.openapp.jsf.domain.Event event = (com.openapp.jsf.domain.Event) intent.getSerializableExtra(EXTRA_JOBPOST);
        ((TextView)findViewById(R.id.title)).setText(event.getTitle());
        ((TextView)findViewById(R.id.venue)).setText(event.getVenue());
        ((TextView)findViewById(R.id.datetime)).setText(event.getDatetime());
        ((TextView)findViewById(R.id.description)).setText(event.getDescription());
    }

}

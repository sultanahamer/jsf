package com.openapp.jsf.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class JobPost extends ActionBarActivity {

    public static final String EXTRA_JOBPOST = "JOBPOST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        com.openapp.jsf.domain.JobPost jobPost = (com.openapp.jsf.domain.JobPost) intent.getSerializableExtra(EXTRA_JOBPOST);
        ((TextView)findViewById(R.id.title)).setText(jobPost.getTitle());
        ((TextView)findViewById(R.id.skills)).setText(jobPost.getSkills());
        ((TextView)findViewById(R.id.howtoapply)).setText(jobPost.getHowToApply());
        ((TextView)findViewById(R.id.description)).setText(jobPost.getDescription());
        ((TextView)findViewById(R.id.employer)).setText(jobPost.getEmployer());
        ((TextView)findViewById(R.id.experience)).setText(jobPost.getExperience());

    }
}

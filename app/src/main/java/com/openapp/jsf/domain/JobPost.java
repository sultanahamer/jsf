package com.openapp.jsf.domain;


import com.openapp.jsf.database.Items;

import org.json.JSONException;
import org.json.JSONObject;

public class JobPost extends Item {
    private String employer;
    private String howToApply;
    private String description;
    private String skills;
    private String experience;

    public String getSkills() {
        return skills;
    }

    public String getExperience() {
        return experience;
    }

    public JobPost(JSONObject jobPost, boolean read, String timestamp ) throws JSONException {
        super(jobPost.getString("title"), read, timestamp, Items.TYPE_JOB);
        this.employer = jobPost.getString("employer");
        this.howToApply = jobPost.getString("howtoapply");
        this.description = jobPost.getString("description");
        this.skills = jobPost.getString("skills");
        this.experience = jobPost.getString("experience");
    }


    public String getEmployer() {
        return employer;
    }

    public String getHowToApply() {
        return howToApply;
    }

    public String getDescription() {
        return description;
    }

}

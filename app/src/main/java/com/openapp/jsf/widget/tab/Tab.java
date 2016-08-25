package com.openapp.jsf.widget.tab;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.openapp.jsf.activities.R;
import com.openapp.jsf.database.Items;
import com.openapp.jsf.domain.Event;
import com.openapp.jsf.domain.Item;
import com.openapp.jsf.domain.JobPost;
import com.openapp.jsf.domain.News;

import java.util.ArrayList;

public class Tab {
    private int index;
    private boolean selected = false;
    int selectedTabDecoratorColor = Color.rgb(255, 255, 255);
    int normalTabDecoratorColor = Color.rgb(18, 115, 191);

    private ListView list;
    private Activity context;
    private View tab;
    private View decorator;
    private TextView updates;
    private JSFListAdapter adapter;
    private TabSelectionHandler tabSelectionHandler;

    public Tab(View tab, ListView list, Activity context,int index) {
        this.index = index;
        this.list= list;
        this.context = context;
        this.tab = tab;
        this.tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabSelectionHandler != null)
                    tabSelectionHandler.onTabSelectionChanged(Tab.this);
            }
        });
        getUIComponents(index);
        this.decorator.setBackgroundColor(normalTabDecoratorColor);
    }

    private void getUIComponents(int tabIndex) {
        switch(tabIndex){
            case Items.TYPE_JOB:
                this.decorator = context.findViewById(R.id.jobs_decorator);
                this.adapter = new JSFListAdapter(context) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View listItem = (convertView == null) ? inflater.inflate(R.layout.list_item_jobs, null) : convertView;
                        Item item = items.get(position);
                        setText(R.id.JobTitle, listItem, item.getTitle());
                        setText(R.id.Employer, listItem, ((JobPost)item).getEmployer());
                        return listItem ;
                    }
                };
                break;
            case Items.TYPE_EVENT:
                this.decorator = context.findViewById(R.id.events_decorator);
                this.adapter = new JSFListAdapter(context) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View listItem = (convertView == null) ? inflater.inflate(R.layout.list_item_events, null) : convertView;
                        Event event = (Event) items.get(position);
                        setText(R.id.event_title_text, listItem, event.getTitle());
                        setText(R.id.event_description_text, listItem, event.getDescription());
                        setText(R.id.event_venue_text, listItem, event.getVenue());
                        setText(R.id.event_time_text, listItem, event.getDatetime());
                        return listItem ;
                    }
                };
                break;
            case Items.TYPE_NEWS:
                this.decorator = context.findViewById(R.id.news_decorator);
                this.adapter = new JSFListAdapter(context) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View listItem = (convertView == null) ? inflater.inflate(R.layout.list_item_news, null) : convertView;
                        Item item = items.get(position);
                        setText(R.id.news_title, listItem, item.getTitle());
                        setText(R.id.news_content, listItem, ((News) item).getContent());
                        return listItem ;
                    }
                };
                break;

        }
    }

    public void setOnTabSelectionHandler(TabSelectionHandler tabSelectionHandler) {
        this.tabSelectionHandler = tabSelectionHandler;
    }

    public void deselectTab(){
        selected = false;
        decorator.setBackgroundColor(normalTabDecoratorColor);
    }

    public void selectTab(){
        decorator.setBackgroundColor(selectedTabDecoratorColor);
        list.setAdapter(adapter);
        selected = true;
    }

    public int getIndex() {
        return index;
    }
    public void setContent(ArrayList items){
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }

}
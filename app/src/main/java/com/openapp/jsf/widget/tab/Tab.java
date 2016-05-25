package com.openapp.jsf.widget.tab;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.openapp.jsf.activities.R;
import com.openapp.jsf.database.Items;
import com.openapp.jsf.domain.Item;

import java.util.ArrayList;

public class Tab {
    private int index;
    private boolean selected = false;
    int selectedTabColor = Color.rgb(254, 210, 49);
    int normalTabColor = Color.rgb(52, 207, 253);
    private ListView list;
    private Activity context;
    private View tab;
    private View seperator,circle;
    private TextView updates;
    private ArrayAdapter<Item> adapter;
    private TabSelectionHandler tabSelectionHandler;

    public Tab(View tab, ListView list, Activity context,int index) {
        this.index = index;
        this.list= list;
        this.context = context;
        this.tab = tab;
        this.tab.setBackgroundColor(normalTabColor);
        this.tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabSelectionHandler != null)
                    tabSelectionHandler.onTabSelectionChanged(Tab.this);
            }
        });
        getUIComponents(index);
    }

    private void getUIComponents(int index) {
        switch(index){
            case Items.TYPE_JOB:
                this.seperator = context.findViewById(R.id.seperator_jobs);
                this.updates = (TextView) context.findViewById(R.id.updates_count_jobs);
                this.circle =  context.findViewById(R.id.circle_jobs);
                break;
            case Items.TYPE_EVENT:
                this.seperator = context.findViewById(R.id.seperator_events);
                this.updates = (TextView) context.findViewById(R.id.updates_count_events);
                this.circle =  context.findViewById(R.id.circle_events);
                break;
            case Items.TYPE_NEWS:
                this.seperator = context.findViewById(R.id.seperator_news);
                this.updates = (TextView) context.findViewById(R.id.updates_count_news);
                this.circle =  context.findViewById(R.id.circle_news);
                break;

        }
    }

    public void setOnTabSelectionHandler(TabSelectionHandler tabSelectionHandler) {
        this.tabSelectionHandler = tabSelectionHandler;
    }

    public void deselectTab(){
        selected = false;
        tab.setBackgroundColor(normalTabColor);
    }

    public void selectTab(){
        tab.setBackgroundColor(selectedTabColor);
        list.setAdapter(adapter);
        selected = true;
    }

    public int getIndex() {
        return index;
    }
    public void setContent(ArrayList items){
        adapter = new ListAdapter(context, items);
        int count_unread = 0;
        for(Object obj:items){
            Item item = (Item) obj;
            if(item.isRead()) continue;
            count_unread++;
        }
        if(count_unread > 0){
            System.out.println(updates.getText());
            updates.setText(String.valueOf(count_unread));
            showUpdatesCount();
        }
        else hideUpdatesCount();
    }

    private void hideUpdatesCount() {
        seperator.setVisibility(View.GONE);
        updates.setVisibility(View.GONE);
        circle.setVisibility(View.GONE);
    }

    private void showUpdatesCount() {
        seperator.setVisibility(View.VISIBLE);
        updates.setVisibility(View.VISIBLE);
        circle.setVisibility(View.VISIBLE);

    }
}
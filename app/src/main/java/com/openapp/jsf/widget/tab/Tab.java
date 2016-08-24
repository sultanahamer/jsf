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
    int selectedTabDecoratorColor = Color.rgb(255, 255, 255);
    int normalTabDecoratorColor = Color.rgb(18, 115, 191);

    private ListView list;
    private Activity context;
    private View tab;
    private View decorator;
    private TextView updates;
    private ArrayAdapter<Item> adapter;
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

    private void getUIComponents(int index) {
        switch(index){
            case Items.TYPE_JOB:
                this.decorator = context.findViewById(R.id.jobs_decorator);
                break;
            case Items.TYPE_EVENT:
                this.decorator = context.findViewById(R.id.events_decorator);
                break;
            case Items.TYPE_NEWS:
                this.decorator = context.findViewById(R.id.news_decorator);
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
        adapter = new ListAdapter(context, items);
    }

}
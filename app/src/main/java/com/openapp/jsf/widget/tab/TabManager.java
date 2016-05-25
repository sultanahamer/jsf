package com.openapp.jsf.widget.tab;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.openapp.jsf.activities.R;


public class TabManager implements TabSelectionHandler{

    private int numberOfTabs = 3;
    private int selectedTabIndex = 0;

    private Tab[] tabs;
    public static final int TAB_JOBS = 0, TAB_EVENTS = 1, TAB_NEWS = 2;
    ListView list;
    Activity activity;
    ArrayAdapter adapter;
    public static int layout = R.layout.activity_home;

    public TabManager(Activity activity){
        this.activity = activity;
        tabs = new Tab[numberOfTabs];
        initalize();
    }

    private void getAllUIComponents(){
        list  = (ListView) activity.findViewById(R.id.list_view);
        list.setDivider(null);
        tabs[TAB_JOBS] = new Tab(activity.findViewById(R.id.JobsTab), list, activity, TAB_JOBS);
        tabs[TAB_EVENTS] = new Tab(activity.findViewById(R.id.EventsTab), list, activity, TAB_EVENTS);
        tabs[TAB_NEWS] = new Tab(activity.findViewById(R.id.NewsTab), list, activity, TAB_NEWS);
    }

    private void initalize() {
        getAllUIComponents();
        addClickListenersForTabHeaders();
    }

    private void addClickListenersForTabHeaders() {
        for(int i=0;i<numberOfTabs;i++){
            tabs[i].setOnTabSelectionHandler(this);
        }
    }

    public void selectTab(int index) {
        tabs[selectedTabIndex].deselectTab();
        tabs[index].selectTab();
        selectedTabIndex = index;
    }

    @Override
    public void onTabSelectionChanged(Tab tab) {
        tabs[selectedTabIndex].deselectTab();
        selectedTabIndex = tab.getIndex();
        tab.selectTab();
    }

    public Tab getTab(int index) {
        return tabs[index];
    }

    public void setOnItemClicked(final TabContentClickHandler onItemClicked) {

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClicked.onClick(view, position, selectedTabIndex);
            }
        });
    }

    public void highlightDefaultTab()  {
        selectTab(selectedTabIndex);
    }
}


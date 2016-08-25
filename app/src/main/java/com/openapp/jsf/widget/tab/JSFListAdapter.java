package com.openapp.jsf.widget.tab;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.openapp.jsf.activities.R;
import com.openapp.jsf.domain.Item;
import com.openapp.jsf.domain.JobPost;

import java.util.ArrayList;

public abstract class JSFListAdapter extends ArrayAdapter<Item> {
    private Context context;
    protected ArrayList<Item> items;
    LayoutInflater inflater;
    public JSFListAdapter(Context context) {
        super(context, -1);
        inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    protected void setText(int id, View view, String text){
        ((TextView) view.findViewById(id)).setText(text);
    }


    public abstract View getView(int position, View convertView, ViewGroup parent);
    public void setItems(ArrayList<Item> items){
        this.items = items;
        this.clear();
        for(Item item : this.items){
            this.add(item);
        }
    }

}

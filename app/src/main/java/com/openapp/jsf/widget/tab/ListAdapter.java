package com.openapp.jsf.widget.tab;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.openapp.jsf.activities.R;
import com.openapp.jsf.domain.Item;
import com.openapp.jsf.domain.JobPost;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Item> {
    private Context context;
    int unreadItemColor = Color.rgb(34, 125, 195);
    int readItemColor = Color.rgb(183,181,182);
    private ArrayList<Item> items;
    LayoutInflater inflater;
    public ListAdapter(Context context, ArrayList<Item> items) {
        super(context, -1, items);
        this.items = items;
        inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    private void setText(int id, View view, String text){
        ((TextView) view.findViewById(id)).setText(text);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = inflater.inflate(R.layout.list_item, null);
        Item item = items.get(position);
        setText(R.id.JobTitle, listItem, item.getTitle());
        setText(R.id.Employer, listItem, ((JobPost)item).getEmployer());
        return listItem ;
    }
}

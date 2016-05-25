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
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = inflater.inflate(R.layout.list_item, null);
        TextView textView = (TextView) listItem.findViewById(R.id.text);
        textView.setText(items.get(position).getTitle());
        View item = listItem.findViewById(R.id.item);
        if(items.get(position).isRead())
            item.setBackgroundResource(R.drawable.round_rect_shape_read_item);
        else
            item.setBackgroundResource(R.drawable.round_rect_shape_unread_item);


        return listItem ;
    }
}

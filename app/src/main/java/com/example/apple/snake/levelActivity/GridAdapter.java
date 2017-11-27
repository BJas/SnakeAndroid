package com.example.apple.snake.levelActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.snake.R;


/**
 * Created by apple on 27.11.17.
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;

    public Integer[] categoriesIds = { R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four,
            R.drawable.five, R.drawable.six };
    public String[] categories = {
            "Level", "Level", "Level", "Level", "Level", "Level"

    };
    // Constructor
    public GridAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public Object getItem(int position) {
        return categories[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItem viewHolder;


        if (convertView == null) {

            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.gridview_cell, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.textViewItem = (TextView) convertView.findViewById(R.id.tv_text);

            viewHolder.imageViewItem = (ImageView) convertView.findViewById(R.id.item_img);
            // store the holder with the view.
            convertView.setTag(viewHolder);


        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        // object item based on the position

        // get the TextView from the ViewHolder and then set the text (item name) and tag (item ID) values
        viewHolder.textViewItem.setText(categories[position]);
        viewHolder.textViewItem.setTag(position);

        viewHolder.imageViewItem.setImageResource(categoriesIds[position]);


        return convertView;

    }

    // our ViewHolder.
// caches our TextView
    static class ViewHolderItem {
        TextView textViewItem;
        ImageView imageViewItem;
    }


}
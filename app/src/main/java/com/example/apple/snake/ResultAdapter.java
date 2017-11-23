package com.example.apple.snake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by apple on 21.11.17.
 */

public class ResultAdapter extends ArrayAdapter<Score> {
    public ResultAdapter(Context context, ArrayList<Score> scores) {
        super(context, 0, scores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Score score = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }
        // Lookup view for data population
        TextView nameTextView = (TextView) convertView.findViewById(R.id.resultName);
        TextView scoreTextView = (TextView) convertView.findViewById(R.id.resultScore);
        // Populate the data into the template view using the data object
        nameTextView.setText(score.name);
        scoreTextView.setText(String.valueOf(score.score));
        // Return the completed view to render on screen
        return convertView;
    }
}

package com.example.apple.snake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtView = (TextView) findViewById(R.id.aboutText);
        txtView.setText("Play the original snake challenge, or venture into the land of wild variations. " +
                "We have snake games which feature vivid colors and smooth. Play in a variety of environments. " +
                "In our adventures, you’ll control your cobra to eat apples, avoid negative power-ups. " +
                "Just make sure to avoid running into yourself, or you’ll crash and burn!");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


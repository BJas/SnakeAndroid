package com.example.apple.snake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    List<Score> listScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Score> arrayOfUsers = new ArrayList<Score>();
        ResultAdapter adapter = new ResultAdapter(this, arrayOfUsers);
        listScore = Score.listAll(Score.class);
        Collections.sort(listScore, new Comparator<Score>() {
            public int compare(Score c1, Score c2) {
                if (c1.score > c2.score) return -1;
                if (c1.score < c2.score) return 1;
                return 0;
            }});

        adapter.addAll(listScore);
        ListView listView = (ListView) findViewById(R.id.lvScore);
        listView.setAdapter(adapter);
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

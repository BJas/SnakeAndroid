package com.example.apple.snake.levelActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.apple.snake.Main2Activity;
import com.example.apple.snake.MainActivity;
import com.example.apple.snake.R;
import com.example.apple.snake.SeekBarValue;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private long id  = 1;
    private int levelId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new GridAdapter(this));

        List<SeekBarValue> seekBarValueList = SeekBarValue.listAll(SeekBarValue.class);
        if(seekBarValueList.size() == 1) {
            SeekBarValue seekBarValue = SeekBarValue.findById(SeekBarValue.class, id);
            levelId = seekBarValue.getLevel();
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(position < levelId) {

                    Intent start = new Intent(CategoryActivity.this, MainActivity.class);
                    start.putExtra("position", position);
                    startActivity(start);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You are at level " + String.valueOf(levelId), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent start = new Intent(CategoryActivity.this, Main2Activity.class);
            startActivity(start);
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.apple.snake;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class OptionActivity extends AppCompatActivity implements View.OnClickListener {

    SeekBar customSeekbar;
    TextView progress;
    SeekBarValue seekBarValue;
    private long id = 1;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customSeekbar = (SeekBar)findViewById(R.id.seekBar);
        progress = (TextView)findViewById(R.id.speedText);


            seekBarValue = SeekBarValue.findById(SeekBarValue.class, id);
            customSeekbar.setProgress(seekBarValue.value);
            progress.setText(" " + seekBarValue.value);

            customSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                SeekBarValue seekBarValueFind = SeekBarValue.findById(SeekBarValue.class, id);
                seekBarValueFind.value = i;
                seekBarValueFind.save();
                progress.setText( " " + i);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        SeekBarValue seekBarValueFindId = SeekBarValue.findById(SeekBarValue.class, id);
        toast = Toast.makeText(getApplicationContext(),
                "Background image changed", Toast.LENGTH_SHORT);
        switch (view.getId()){
            case R.id.background1:
                seekBarValueFindId.backgroundId = 1;
                seekBarValueFindId.save();

                toast.show();

                break;
            case R.id.background2:
                seekBarValueFindId.backgroundId = 2;
                seekBarValueFindId.save();
                toast.show();
                break;
            case R.id.background3:
                seekBarValueFindId.backgroundId = 3;
                seekBarValueFindId.save();
                toast.show();
                break;
            case R.id.background4:
                seekBarValueFindId.backgroundId = 4;
                seekBarValueFindId.save();
                toast.show();
                break;
            default:
                break;
        }
    }
}

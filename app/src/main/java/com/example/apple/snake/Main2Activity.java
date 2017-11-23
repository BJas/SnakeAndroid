package com.example.apple.snake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    public Button btn;
    public Button btnResult;
    public Button btnAbout;
    public Button btnOption;

    public void init() {
        btn = (Button) findViewById(R.id.startButton);

        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent start = new Intent(Main2Activity.this, MainActivity.class);
                                       startActivity(start);
                                   }
                               }
        );

        btnResult = (Button) findViewById(R.id.buttonResult);

        btnResult.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent start = new Intent(Main2Activity.this, ResultActivity.class);
                                       startActivity(start);
                                   }
                               }
        );

        btnAbout = (Button) findViewById(R.id.aboutButton);

        btnAbout.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Intent start = new Intent(Main2Activity.this, AboutActivity.class);
                                             startActivity(start);
                                         }
                                     }
        );

        btnOption = (Button) findViewById(R.id.buttonOption);

        btnOption.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent start = new Intent(Main2Activity.this, OptionActivity.class);
                                            startActivity(start);
                                        }
                                    }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        List<SeekBarValue> values = SeekBarValue.listAll(SeekBarValue.class);
        if(values.size() == 0) {
            SeekBarValue seekBarValue = new SeekBarValue(1, 1);
            seekBarValue.save();
            Log.d("seekBarValue",String.valueOf(seekBarValue.value));
        }
        init();
    }
}

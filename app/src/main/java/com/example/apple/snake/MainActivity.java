package com.example.apple.snake;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import com.example.apple.snake.engine.GameEngine;
import com.example.apple.snake.enums.Direction;
import com.example.apple.snake.enums.GameState;
import com.example.apple.snake.views.SnakeView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private MediaPlayer backgroundMusic;
    private MediaPlayer loseMusic;
    private GameEngine gameEngine;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    private long updateDelay = 700; //speed of snake
    Intent i;
    private float prevX, prevY;
    Context context;
    private View view;
    private AlertDialog.Builder alertDialog;
    private TextView resTextView;
    private boolean add = false;
    private boolean pause = false;
    ImageButton btn;
    ImageButton btn1;
    private long id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameEngine = new GameEngine();
        gameEngine.initGame();

        backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.back);
        backgroundMusic.setLooping(true);
        loseMusic = MediaPlayer.create(MainActivity.this, R.raw.lose);

        List<SeekBarValue> seekBarValueList = SeekBarValue.listAll(SeekBarValue.class);
        if(seekBarValueList.size() == 1) {
            SeekBarValue seekBarValue = SeekBarValue.findById(SeekBarValue.class, id);
            updateDelay = 700 - seekBarValue.value*50;

            switch (seekBarValue.backgroundId){
                case 1:
                    getWindow().getDecorView().setBackgroundResource(R.drawable.white);
                    break;
                case 2:
                    getWindow().getDecorView().setBackgroundResource(R.drawable.sea);
                    break;
                case 3:
                    getWindow().getDecorView().setBackgroundResource(R.drawable.sand1);
                    break;
                case 4:
                    getWindow().getDecorView().setBackgroundResource(R.drawable.rock1);
                    break;
                default:
                    break;
            }

        }
        initDialog();
        pauseState();
        i = getIntent();
        String score = i.getStringExtra("score");
        snakeView = (SnakeView) findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);
        startUpdateHandler();
    }

    private void startUpdateHandler() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.Update();

                TextView txtView = (TextView) findViewById(R.id.score);
                txtView.setText(String.valueOf("Score: " + gameEngine.score));

                if(gameEngine.getCurrentGameState() == GameState.Running && pause == false){
                    handler.postDelayed(this, updateDelay);
                    backgroundMusic.start();
                }
                if(pause) {
                    backgroundMusic.pause();
                }

                if(gameEngine.getCurrentGameState() == GameState.Lost){
                    backgroundMusic.stop();
                    loseMusic.start();
                    OnGameLost();
                }
                snakeView.setSnakeViewMap(gameEngine.getMap());
                snakeView.invalidate(); //refresh
            }
        }, updateDelay);
    }

    private void pauseState() {
// when you click this demo button
        btn = (ImageButton)findViewById(R.id.btnPause);
        btn1 = (ImageButton) findViewById(R.id.btnBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pause) {
                    btn.setImageResource(R.drawable.play);
                    btn1.setImageResource(R.drawable.back);
                    pause = true;
                }
                else {
                    btn.setImageResource(R.drawable.pause);
                    btn1.setImageResource(android.R.color.transparent);
                    pause = false;
                    startUpdateHandler();
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pause) {
                    Intent start = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(start);
                }
            }
        });

    }

    private void  OnGameLost()
    {
        Log.d("lost", String.valueOf(gameEngine.score));
        removeView();
        add = true;
        alertDialog.show();
    }

    private void removeView(){
        if(view.getParent()!=null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private void initDialog() {
//        loseMusic.stop();
        Log.d("lost123", String.valueOf(gameEngine.score));
        alertDialog = new AlertDialog.Builder(MainActivity.this);
        view = this.getLayoutInflater().inflate(R.layout.result_save, null);
        alertDialog.setView(view);
        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(add) {
                    add = false;
                    EditText title = (EditText) view.findViewById(R.id.cr_title);
                    Score scoreClass = new Score(gameEngine.score, title.getText().toString());
                    Log.d("title", scoreClass.getName());
                    scoreClass.save();
                    gameEngine.score = 0;
                    dialog.dismiss();
                    Intent start = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(start);
                    title.getText().clear();
                }
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevX = motionEvent.getX();
                prevY = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                float newX = motionEvent.getX();
                float newY = motionEvent.getY();

                if(Math.abs(newX - prevX) > Math.abs(newY - prevY)) {  //left right dir
                    if(newX > prevX) {
                        gameEngine.UpdateDirection(Direction.East);
                    } else {
                        gameEngine.UpdateDirection(Direction.West);
                    }
                }
                else {  // up down dir

                    if(newY > prevY) { //down
                        gameEngine.UpdateDirection(Direction.South);
                    } else {
                        gameEngine.UpdateDirection(Direction.North);
                    }
                }
                break;
        }
        return true;
    }
}

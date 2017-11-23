package com.example.apple.snake;

import android.content.Intent;

import com.orm.SugarRecord;

/**
 * Created by apple on 20.11.17.
 */

public class Score extends SugarRecord<Score> {
    String name;
    Integer score;

    public Score(){
    }

    public Score(Integer score, String name){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}

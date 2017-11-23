package com.example.apple.snake;

import com.orm.SugarRecord;

/**
 * Created by apple on 21.11.17.
 */

public class SeekBarValue extends SugarRecord<SeekBarValue> {
    Integer value;
    Integer backgroundId;

    public SeekBarValue(){
    }

    public SeekBarValue(Integer value, Integer backgroundId){

        this.value = value;
        this.backgroundId = backgroundId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(Integer backgroundId) {
        this.backgroundId = backgroundId;
    }
}

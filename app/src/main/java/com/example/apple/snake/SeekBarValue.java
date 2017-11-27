package com.example.apple.snake;

import com.orm.SugarRecord;

/**
 * Created by apple on 21.11.17.
 */

public class SeekBarValue extends SugarRecord<SeekBarValue> {
    Integer value;
    Integer backgroundId;
    Integer level;

    public SeekBarValue(){
    }

    public SeekBarValue(Integer value, Integer backgroundId, Integer level){

        this.value = value;
        this.backgroundId = backgroundId;
        this.level = level;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}

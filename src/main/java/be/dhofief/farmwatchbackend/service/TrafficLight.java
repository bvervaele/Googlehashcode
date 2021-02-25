package be.dhofief.farmwatchbackend.service;

import java.util.ArrayList;
import java.util.List;

public class TrafficLight {

    private long score;
    private long cycleValue;
    private String streetname;

    public TrafficLight(String streetname){
        this.score = 0L;
        this.streetname = streetname;
    }

    public void usedByCar(){
        score++;
    }

    public long getScore(){
        return score;
    }

    public void setCycleValue(long l){
        this.cycleValue = l;
    }

    public Long getCycleValue(){
        return cycleValue;
    }

    public String toString(){
        return streetname +  " " + ((int) cycleValue);
    }
}

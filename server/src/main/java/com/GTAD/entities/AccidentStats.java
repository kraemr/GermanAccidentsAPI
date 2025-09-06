package com.GTAD.entities;

import java.util.Map;

public class AccidentStats {
    private int year;   
    private int count;
    private float totalAccidentPercentage;

    AccidentStats() {

    }

    AccidentStats( 
        int year,
        int count,
        float totalAccidentPercentage
    ) {
        this.year = year;
        this.count = count;
        this.totalAccidentPercentage = totalAccidentPercentage;    
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // Getter and Setter for count
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // Getter and Setter for totalAccidentPercentage
    public float getTotalAccidentPercentage() {
        return totalAccidentPercentage;
    }

    public void setTotalAccidentPercentage(float totalAccidentPercentage) {
        this.totalAccidentPercentage = totalAccidentPercentage;
    }
}
package com.example.sunamialert;

public class Earthquake {
    private double magnitude;
    private String location;
    private  long timeInMilliSeconds;
    private String url;
    public Earthquake(Double magnitude, String location, long timeInMilliSeconds,String url) {
        this.magnitude = magnitude;
        this.location = location;
        this.timeInMilliSeconds = timeInMilliSeconds;
        this.url=url;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public long getDate() {
        return timeInMilliSeconds;
    }

    public String getUrl(){return url;}
}

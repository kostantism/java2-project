package hua.java2.project.cooking;

import java.util.Objects;

public class Time implements UnitConvertion{

    private float time;
    private String timeUnit;

    public Time(float time, String timeUnit) {
        this.time = time;
        this.timeUnit = timeUnit;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    //μετατροπη μοναδας
    @Override
    public String convert(float q, String name) {

        if(name.equals("minutes")) {
            return convertToHours((int) q);
        } else if(name.equals("hours")) {
            return convertToMinutes(q);
        } else {
            throw new IllegalArgumentException();
        }

    }

    private String convertToHours(int m) {
        int hours;
        int minutes;

        if(m < 60){
            return m + " minutes";

        } else if((m % 60) != 0){
            hours = m/60;
            minutes = m%60;
            return hours + " hours " + minutes + " minutes";

        } else {
            hours = m/60;
            return hours + " hours";
        }
    }

    private String convertToMinutes(float h) {
        float minutes;

        minutes = h*60;

        return minutes + " minutes";
    }

    public int convertToMinutes(String u, int h) {
        int minutes = 0;

        if(u.equals("hours")) {
            minutes = h * 60;
        } else if(u.equals("minutes")) {
            minutes = h;
        }

//        minutes = h*60;

        return minutes;
    }

    public int convertToSeconds(String u, float t) {
        int sec = 0;

        if(u.equals("minutes")) {
            sec = (int)(t * 60);
        } else if(u.equals("hours")) {
            sec = (int) (t * 60 * 60);
        } else if(u.equals("seconds")) {
            sec = (int) t;
        }

        return sec;
    }

    public int addMinutes(int q, String name){
        int minutes = 0;

        if(name.equals("hours")){
            minutes += q*60;
        } else if (name.equals("minutes")) {
            minutes += q;
        }
        return minutes;
    }
}

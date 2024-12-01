package hua.java2.project.cooking;

import java.util.Objects;

public class Time implements UnitConvertion{

    private String time;

    public Time(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String convert(float q, String name) {
        float x = 0;

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
}

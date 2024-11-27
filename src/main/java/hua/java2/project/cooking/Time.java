package hua.java2.project.cooking;

public class Time implements UnitConvertion{

    private int time;

    public Time(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public float convert(int q, String name) {
        return 0;
    }
}

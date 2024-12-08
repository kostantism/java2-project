package hua.java2.project.cooking;

import java.util.ArrayList;

public class Step {

    private String description;
    private float stepTime;///////////////
    private String timeUnit;

    public Step(String description, float stepTime, String timeUnit) {
        this.description = description;
        this.stepTime = stepTime;
        this.timeUnit = timeUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String step) {
        this.description = description;
    }

    public float getStepTime() {
        return stepTime;
    }

    public void setStepTime(float stepTime) {
        this.stepTime = stepTime;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }
}

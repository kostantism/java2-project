package hua.java2.project.cooking;

public class Step {

    private String description;
    private int stepTime;
    private String timeUnit;

    public Step(String description, int stepTime, String timeUnit) {
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

    public int getStepTime() {
        return stepTime;
    }

    public void setStepTime(int stepTime) {
        this.stepTime = stepTime;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }
}

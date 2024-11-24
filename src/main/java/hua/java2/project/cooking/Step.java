package hua.java2.project.cooking;

public class Step {

    private String description;
    private int stepTime;

    public Step(String description, int stepTime) {
        this.description = description;
        this.stepTime = stepTime;
    }

    public String getStep() {
        return description;
    }

    public void setStep(String step) {
        this.description = description;
    }

    public int getStepTime() {
        return stepTime;
    }

    public void setStepTime(int stepTime) {
        this.stepTime = stepTime;
    }
}

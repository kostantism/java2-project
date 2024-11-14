package hua.java2.project.cooking;

public class Step {

    private String step;
    private int stepTime;

    public Step(String step, int stepTime) {
        this.step = step;
        this.stepTime = stepTime;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public int getStepTime() {
        return stepTime;
    }

    public void setStepTime(int stepTime) {
        this.stepTime = stepTime;
    }
}

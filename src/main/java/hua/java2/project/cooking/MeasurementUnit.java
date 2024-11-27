package hua.java2.project.cooking;

public class MeasurementUnit implements UnitConvertion{

    private String name;

    public MeasurementUnit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public float convert(int q, String name) {
        

        return 0;
    }
}

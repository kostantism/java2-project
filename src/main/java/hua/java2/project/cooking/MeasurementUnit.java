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
    public String convert(float q, String name) {


        return "";
    }

    private String convertToKg(int g) {
        int kg;
        int gr;

        if(g < 1000){
            return g + " gr";

        } else if((g % 1000) != 0){
            kg = g/1000;
            gr = g%1000;
            return kg + " kg " + gr + " gr";

        } else {
            kg = g/1000;
            return kg + " kg";
        }
    }

    private String convertToGr(float kg) {
        float gr;

        gr = kg*1000;

        return gr + " gr";
    }

    private String convertToL() {
        return "";
    }

    private String convertToMl() {
        return "";
    }

    private String addNewUnit() {
        return "";
    }
}

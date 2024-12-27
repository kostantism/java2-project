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

    //μετατροπη μοναδας
    @Override
    public String convert(float q, String name) {

        if(name.equals("gr")) {
            return convertToKg((int) q);
        } else if(name.equals("kg")) {
            return convertToGr(q);
        } else if(name.equals("ml")) {
            return convertToL((int) q);
        } else if(name.equals("l")) {
            return convertToMl(q);
        } else {
            throw new IllegalArgumentException();
        }

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

    private String convertToL(int m) {
        int l;
        int ml;

        if(m < 1000){
            return m + " ml";

        } else if((m % 1000) != 0){
            l = m/1000;
            ml = m%1000;
            return l + " l " + ml + " ml";

        } else {
            l = m/1000;
            return l + " l";
        }
    }

    private String convertToMl(float l) {
        float ml;

        ml = l*1000;

        return ml + " ml";
    }

    public int addGr(float q, String name){
        int gr = 0;

        if(name.equals("kg")){
            gr += q*1000;
        } else if (name.equals("gr")) {
            gr += q;
        }
        return gr;
    }

    public int addMl(float q, String name){
        int ml = 0;

        if(name.equals("l")){
            ml += q*1000;
        } else if (name.equals("ml")) {
            ml += q;
        }
        return ml;
    }
}

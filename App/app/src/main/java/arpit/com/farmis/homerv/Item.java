package arpit.com.farmis.homerv;

import java.util.ArrayList;

public class Item {
    ArrayList<Float> temps;
    ArrayList<Float> humidity;
    ArrayList<Float> level;
    ArrayList<Float> siloLevel;
    int count;

    public Item(ArrayList<Float> temps, ArrayList<Float> humidity, ArrayList<Float> level,
                ArrayList<Float> siloLevel, int count) {
        this.temps = temps;
        this.humidity = humidity;
        this.level = level;
        this.count = count;
        this.siloLevel = siloLevel;
    }


    public Item() {

    }

    public ArrayList<Float> getSiloLevel() {
        return siloLevel;
    }

    public void setSiloLevel(ArrayList<Float> siloLevel) {
        this.siloLevel = siloLevel;
    }

    public ArrayList<Float> getLevel() {
        return level;
    }

    public void setLevel(ArrayList<Float> level) {
        this.level = level;
    }

    public ArrayList<Float> getTemps() {
        return temps;
    }

    public void setTemps(ArrayList<Float> temps) {
        this.temps = temps;
    }

    public ArrayList<Float> getHumidity() {
        return humidity;
    }

    public void setHumidity(ArrayList<Float> humidity) {
        this.humidity = humidity;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}

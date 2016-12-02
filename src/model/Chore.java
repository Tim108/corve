package model;

/**
 * Created by Tim on 23/11/2016.
 */
public class Chore {
    private String name;
    private String description;
    private int interval;

    public Chore(String name, String description, int interval) {
        this.name = name;
        this.description = description;
        this.interval = interval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return name;
    }
}

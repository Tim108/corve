package corve.util;

public class Chore {
    private int id;
    private String name;
    private int interval;

    public Chore(int id, String name, int interval) {
        this.id = id;
        this.name = name;
        this.interval = interval;
    }


    public String getName() {
        return name;
    }

    public int getInterval() {
        return interval;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}

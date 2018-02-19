package corve.util;


public class Room implements Comparable<Room> {
    private int id;
    private String name;
    private String email;

    public Room(int nr, String name, String email) {
        this.id = nr;
        this.name = name;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int compareTo(Room o) {
        if (id < o.getId()) return -1;
        if (id > o.getId()) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "" + id;
    }
}

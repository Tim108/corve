package model;

/**
 * Created by Tim on 23/11/2016.
 */
public class Room implements Comparable<Room>{
    private int nr;
    private String name;
    private String email;

    public Room(int nr, String name, String email) {
        this.nr = nr;
        this.name = name;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public int getNr() {
        return nr;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int compareTo(Room o) {
        if(nr < o.getNr()) return -1;
        if(nr > o.getNr()) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "" + nr;
    }
}

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Tim on 23/11/2016.
 */
public class Rooms implements Iterator {
    private List<Room> rooms = new ArrayList<>();

    private int startI = 0;
    private int addedI = 0;

    private int current = 0;

    public Rooms(List<Room> rooms, int startI) {
        if (rooms.size() == 0) return;

        this.rooms = rooms;
        this.startI = startI;
        recalcCurrent();
        Collections.sort(rooms);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Room next() {
        increment();
        return rooms.get(current);
    }

    public void removeRoom(int i) { // unsafe, should not be used once system is started
        int rem = -1;
        for (Room r : rooms) {
            if (r.getId() == i) {
                rem = rooms.indexOf(r);
            }
        }
        if (rem != -1) {
            rooms.remove(rem);
        }
    }

    public void add(Room r) {
        removeRoom(r.getId());
        rooms.add(r);
        Collections.sort(rooms);
    }

    public void setStartI(int i) {
        startI = i;
        recalcCurrent();
    }

    private void increment() {
        addedI++;
        recalcCurrent();
    }

    private void recalcCurrent() {
        current = (startI + addedI) % rooms.size();
    }
}

package model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Tim on 23/11/2016.
 */
public class Update {
    public List<Room> fined;
    public List<Room> rewarded;
    public Map<Chore, Room> assigned;
    public int iteration;
    public Date date;

    public Update(List<Room> fined, List<Room> rewarded, Map<Chore, Room> assigned, int iteration, Date date) {
        this.fined = fined;
        this.rewarded = rewarded;
        this.assigned = assigned;
        this.iteration = iteration;
        this.date = date;
    }

    @Override
    public String toString() {
        String r = "";
        r = r + "fined = " + Arrays.toString(fined.toArray()) + "\n";
        r = r + "rewarded = " + Arrays.toString(rewarded.toArray()) + "\n";
        r = r + "assigned = " + Arrays.toString(assigned.entrySet().toArray()) + "\n";
        r = r + "iteration = " + iteration + "\n";
        r = r + "date = " + date.toString() + "\n";

        return r;
    }
}

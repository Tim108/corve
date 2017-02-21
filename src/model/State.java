package model;

import java.util.*;

/**
 * Created by Tim on 23/11/2016.
 */
public class State extends Model{
    private int updateNr = 0;

    private Map<Chore, Rooms> iterators;
    private Map<Chore, Room> assigned;
    private Map<Chore, Room> done;

    public State(Map<Chore, Rooms> iterators) {
        this.iterators = iterators;
        assigned = new HashMap<>();
        for (Chore c: iterators.keySet()) {
            assigned.put(c, null);
        }
        done = new HashMap<>();
    }

    public void update(){
        List<Room> fined = new ArrayList<>();
        List<Room> rewarded = new ArrayList<>();

        for (Chore c: assigned.keySet()) {
            if(updateNr % c.getInterval() != 0) continue;

            if(done.containsKey(c)){
                if(!assigned.get(c).equals(done.get(c))){
                    fined.add(assigned.get(c));
                    rewarded.add(done.get(c));
                }
            } else{
                fined.add(assigned.get(c));
            }

            assigned.put(c, iterators.get(c).next());
        }

        done = new HashMap<>();

        if (updateNr == 0) {
            fined = new ArrayList<>();
            rewarded = new ArrayList<>();
        }

        Calendar cal = Calendar.getInstance();
        Date d = cal.getTime();
        Update r = new Update(fined, rewarded, assigned, updateNr, d);
        updateNr++;

        updates.add(r);
        super.update();
    }

    public void done(Chore c, Room r) {
        done.put(c, r);
    }
}

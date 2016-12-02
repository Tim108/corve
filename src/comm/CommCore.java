package comm;

import model.Chore;
import model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Tim on 25/11/2016.
 */
public class CommCore {
    CommIn ci;
    CommOut co;

    String username;
    String password;

    List<String> refCodes;

    public CommCore(String username, String password) {
        ci = new CommIn();
        co = new CommOut();

        this.username = username;
        this.password = password;

        refCodes = new ArrayList<>();
    }

    public void fine(List<Room> rooms) {
        for (Room r : rooms) {
            co.doSendMail(username, password, r.getEmail(), "Corve: Fined", "Dear " + r.getName() + ",\n\nYou have not done your chore in time. Therefore you have been fined €10." + "\n\nCorve2.0");
        }
    }

    public void reward(List<Room> rooms) {
        for (Room r : rooms) {
            co.doSendMail(username, password, r.getEmail(), "Corve: Rewarded", "Dear " + r.getName() + ",\n\nYou have done someone else's chore. Therefore you have been rewarded €10." + "\n\nCorve2.0");
        }
    }

    public void assign(Map<Chore, Room> assign) {
        refCodes = new ArrayList<>();
        String code = "";
        for (Chore c : assign.keySet()) {
            code = System.currentTimeMillis() + c.hashCode() + "";
            co.doSendMail(username, password, assign.get(c).getEmail(), "Corve: Assignment", "Dear " + assign.get(c).getName() + ",\n\nYou are assigned to do "+ c.getName() + " this week. See description below:\n" + c.getDescription() + "\n\nCorve2.0");
        }
    }

    public void readmails(){
        Map<String, String> responses = ci.check("pop.gmail.com", "pop3", username, password);

    }
}

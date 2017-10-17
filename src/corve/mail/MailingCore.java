package corve.mail;

import corve.util.Chore;
import corve.util.Room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tim on 25/11/2016.
 */
public class MailingCore {
    MailIn ci;
    MailOut co;

    String username;
    String password;

    Map<String, Chore> refCodes;

    public MailingCore(String username, String password) {
        ci = new MailIn();
        co = new MailOut();

        this.username = username;
        this.password = password;

        refCodes = new HashMap<>();
    }

    public void fine(List<Room> rooms) {
        for (Room r : rooms) {
            co.doSendMail(username, password, r.getEmail(), "Core: Fined", "Dear " + r.getName() + ",\n\nYou have not done your chore in time. Therefore you have been fined €10." + "\n\nCorve2.0");
        }
    }

    public void reward(List<Room> rooms) {
        for (Room r : rooms) {
            co.doSendMail(username, password, r.getEmail(), "Core: Rewarded", "Dear " + r.getName() + ",\n\nYou have done someone else's chore. Therefore you have been rewarded €10." + "\n\nCorve2.0");
        }
    }

    public void assign(Map<Chore, Room> assign) {
        refCodes = new HashMap<>();
        String code = "";
        for (Chore c : assign.keySet()) {
            code = System.currentTimeMillis() + c.hashCode() + "";
            co.doSendMail(username, password, assign.get(c).getEmail(), "Core: Assignment - " + code, "Dear " + assign.get(c).getName() + ",\n\nYou are assigned to do " + c.getName() + " this week. See description below:\n" + c.getDescription().replace("<code>", code) + "\n\nCorve2.0");
            refCodes.put(code, c);
        }
    }

    public Map<Chore, String> readmails() {
        Map r = new HashMap();

        Map<String, String> responses = ci.check(username, password);
        for (String subject : responses.keySet()) {
            for (String code : refCodes.keySet()) {
                if (subject.contains(code)) {
                    r.put(refCodes.get(code), responses.get(subject));
                }
            }
        }
        return r;
    }
}

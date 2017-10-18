package corve.notification;

import corve.util.Chore;
import corve.util.Room;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tim on 25/11/2016.
 */
public class MailingCore implements Notifier{
    MailOut co;

    Map<String, Chore> refCodes;

    public MailingCore(String username, String password) {
        co = new MailOut();

        refCodes = new HashMap<>();
    }

    @Override
    public void notifyOfAssignment(String email, Timestamp endDate, String choreName, String code) {
        co.doSendMail(email, "Corve - Get fooked", "get fooked, you have till " + endDate + " to do the " + choreName + ".. when you are done fill in the code: " + code);
    }

    @Override
    public void notifyOfFine(String email, String choreName) {

    }

    @Override
    public void notifyOfReward(String email, String choreName) {

    }
}

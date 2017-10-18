package corve.notification;

import java.sql.Timestamp;

/**
 * Created by Tim on 25/11/2016.
 */
public class MailingCore implements Notifier {
    MailOut co;

    public MailingCore() {
        co = new MailOut();
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

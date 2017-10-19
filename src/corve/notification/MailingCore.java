package corve.notification;

import corve.util.EmailTemplates;

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
        co.doSendMail(email, "Corve - Assignment", EmailTemplates.getTemplate(choreName, endDate.toString(), code));
    }

    @Override
    public void notifyOfFine(String email, String choreName) {

    }

    @Override
    public void notifyOfReward(String email, String choreName) {

    }
}

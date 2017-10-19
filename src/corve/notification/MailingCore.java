package corve.notification;

import corve.util.EmailTemplates;

import java.sql.Timestamp;

public class MailingCore implements Notifier {
    MailOut co;

    public MailingCore() {
        co = new MailOut();
    }

    @Override
    public void notifyOfAssignment(String email, String roomName, Timestamp endDate, String choreName, String code) {
        co.doSendMail(email, "Corve - Assignment", EmailTemplates.getTemplate(choreName, roomName, endDate.toString(), code));
    }

    @Override
    public void notifyOfFine(String email, String roomName, String choreName) {

    }

    @Override
    public void notifyOfReward(String email, String roomName, String choreName) {

    }
}

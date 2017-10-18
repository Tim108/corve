package corve.notification;

import java.sql.Timestamp;

/**
 * Created by Tim on 18/10/2017.
 */
public interface Notifier {
    void notifyOfAssignment(String email, Timestamp endDate, String choreName, String code);
    void notifyOfFine(String email, String choreName);
    void notifyOfReward(String email, String choreName);
}

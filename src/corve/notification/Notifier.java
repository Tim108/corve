package corve.notification;

import java.sql.Timestamp;

public interface Notifier {
    void notifyOfAssignment(String email, String roomName, Timestamp endDate, String choreName, String code);
    void notifyOfFine(String email, String roomName, String choreName);
    void notifyOfReward(String email, String roomName, String choreName);
}

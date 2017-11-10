package corve.model;

import corve.notification.Notifier;
import corve.save.DBController;
import corve.setup.Settings;
import corve.util.Chore;
import corve.util.JobDataTags;
import corve.util.Record;
import corve.util.Room;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Assigner implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // Get all the objects
        JobDataMap data = jobExecutionContext.getJobDetail().getJobDataMap();
        DBController db = (DBController) data.get(JobDataTags.DATABASE);
        Notifier notifier = (Notifier) data.get(JobDataTags.NOTIFIER);
        Chore chore = (Chore) data.get(JobDataTags.CHORE);
        @SuppressWarnings("unchecked")
        List<Room> rooms = (List<Room>) data.get(JobDataTags.ROOMS);

        System.out.println("Assigning for " + chore.getName());

        // Set the deadline
        LocalDateTime deadline = LocalDateTime.now().with(TemporalAdjusters.next(Settings.DEADLINE_DAY)).withHour(Settings.DEADLINE_HOUR).withMinute(0).withSecond(0).withNano(0);
//        LocalDateTime deadline = LocalDateTime.now().plusSeconds(2);

        // Determine who's turn it is
        int lastRoomID = db.getLastRoomID(chore);
        if (lastRoomID == -1) {
            System.out.println("Couldn't find last room ID of " + chore.getName() + " so a random room is picked");
            lastRoomID = ThreadLocalRandom.current().nextInt(0, rooms.size());
        }
        Room room = null;
        for (Room r : rooms) {
            if (r.getId() == lastRoomID) {
                room = rooms.get((rooms.indexOf(r) + 1) % rooms.size());
            }
        }

        // Make it a record and put it in the database
        assert room != null;
        Record record = new Record(room.getId(), chore.getId(), Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(deadline));
        db.addRecord(record);

        notifier.notifyOfAssignment(room.getEmail(), room.getName(), record.getEnd_date(), chore.getName(), record.getCode());

        System.out.println(chore.getName() + " assigned: " + record);
    }
}

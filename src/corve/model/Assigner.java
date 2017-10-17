package corve.model;

import corve.save.DBController;
import corve.util.Chore;
import corve.util.Rooms;
import org.quartz.JobExecutionException;
import corve.util.Record;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created by Tim on 23/11/2016.
 */
public class Assigner {
    private static DayOfWeek DEADLINE_DAY = DayOfWeek.SATURDAY;

    private DBController db;

    private Map<Chore, Rooms> iterators;

    public Assigner(Map<Chore, Rooms> iterators, DBController db) {
        this.iterators = iterators;
        this.db = db;
    }

    /**
     * creates the next set of records and adds them to the record queue
     *
     * @throws JobExecutionException
     */
    public void assign() throws JobExecutionException {
        System.out.println("Assigning..");
        //LocalDateTime deadline = LocalDateTime.now().with(TemporalAdjusters.next(DEADLINE_DAY)).withHour(4).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime deadline = LocalDateTime.now().plusSeconds(2);

        for (Chore c : iterators.keySet()) {
//            deadline.plusWeeks(c.getInterval() - 1);
//            if (LocalDateTime.now().isAfter(deadline)) {
//                deadline.plusWeeks(1);
//            }
            Record r = new Record(iterators.get(c).next().getId(), c.getId(), Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(deadline));
            db.addRecord(r);
        }

        //Todo notify people of their chores

        System.out.println("Assigning done!");
    }

}

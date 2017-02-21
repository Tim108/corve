package model;

import org.quartz.JobExecutionException;
import save.Record;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by Tim on 23/11/2016.
 */
public class Assigner extends Observable {
    private static DayOfWeek DEADLINE_DAY = DayOfWeek.SATURDAY;

    private Map<Chore, Rooms> iterators;

    public Assigner(Map<Chore, Rooms> iterators) {
        this.iterators = iterators;

    }

    public void assign() throws JobExecutionException {
        System.out.println("assigning..");
        List<Record> newRecords = new ArrayList<>();
        LocalDateTime deadline = LocalDateTime.now().with(TemporalAdjusters.next(DEADLINE_DAY)).withHour(2).withMinute(0).withSecond(0).withNano(0);
        if (LocalDateTime.now().isAfter(deadline)) {
            deadline.plusWeeks(1);
        }

        for (Chore c : iterators.keySet()) {
            newRecords.add(new Record(iterators.get(c).next().getId(), c.getId(), Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(deadline)));
        }
        setChanged();
        notifyObservers(newRecords);
        System.out.println("done");
    }

}

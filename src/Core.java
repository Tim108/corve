import comm.CommCore;
import model.*;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import save.DBController;
import save.Record;

import java.util.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by Tim on 23/11/2016.
 */
public class Core implements Observer {
    private static final String MAIL_USER_NAME = "corveesysteem@gmail.com";
    private static final String MAIL_PASSWORD = "39lA*v3a-V*ai(pal*a]s3j0Skla";

    private DBController dbc;
    private Assigner assigner;

    private CommCore comm;

    private Scheduler scheduler;

    public Core() {
        dbc = new DBController();
        //load
        List<Room> rooms = dbc.getRooms();
        List<Chore> chores = dbc.getChores();

        //create assigner
        Map<Chore, Rooms> iterators = new HashMap<>();

        for (int i = 0; i < chores.size(); i++) {
            int start = (int) (Math.floor(rooms.size() / chores.size()) * i);
            Rooms rs = new Rooms(rooms, start);
            iterators.put(chores.get(i), rs);
        }

        assigner = new Assigner(iterators);
        assigner.addObserver(this);

        //create commcore
        comm = new CommCore(MAIL_USER_NAME, MAIL_PASSWORD); // this should be in a file

        //create scheduler
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDataMap jobData = new JobDataMap();
            jobData.put("assigner", assigner);
            JobDetail job = newJob(JobPretender.class).setJobData(jobData).withIdentity("Assigner").build();
//            Trigger trigger = newTrigger().withIdentity("Every Friday").startNow().withSchedule(weeklyOnDayAndHourAndMinute(DateBuilder.SATURDAY, 2, 0)).build();
            Trigger trigger = newTrigger().withIdentity("Every 10 seconds").startNow().withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

//    public void run() {
//        //update system
//        Map<Chore, String> choresDone = comm.readmails();
//        for (Chore c : choresDone.keySet()) {
//            Optional<Room> or = loader.getRooms().stream().filter(room -> room.getEmail().equals(choresDone.get(c))).findAny();
//            if (or.isPresent()) {
//                state.done(c, or.get());
//            }
//        }
//        state.update();
//
//    }

    public void stop() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass() == Assigner.class) {
            dbc.addRecords((List<Record>) arg);
        }
    }
}

package corve.model;

import corve.save.DBController;
import corve.util.JobDataTags;
import org.apache.log4j.BasicConfigurator;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by Tim on 18/10/2017.
 */
public class PunisherManager {
    private static final DayOfWeek ASSIGN_DAY = DayOfWeek.SATURDAY;
    private static final int ASSIGN_HOUR = 4;
    private static final int ASSIGN_MINUTE = 0;

    private DBController db;
    private Scheduler scheduler;

    public PunisherManager(DBController dbc) throws SchedulerException {
        db = dbc;

        BasicConfigurator.configure();

        scheduler = StdSchedulerFactory.getDefaultScheduler();

        // moment to assign
        LocalDateTime ldt = LocalDateTime.now().with(TemporalAdjusters.next(ASSIGN_DAY)).withHour(ASSIGN_HOUR).withMinute(ASSIGN_MINUTE).withSecond(0).withNano(0);
        Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        // make the general job data which is used by all assigners
        JobDataMap jobData = new JobDataMap();
        jobData.put(JobDataTags.DATABASE, db);

        JobDetail job = newJob(Punisher.class).setJobData(jobData).withIdentity("Job-Punisher").build();

        Trigger trigger = newTrigger().withIdentity(JobDataTags.PUNISHER, "Every 10 seconds").startNow().withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever()).build(); // test trigger
//            trigger = newTrigger().withIdentity(JobDataTags.PUNISHER, "Trigger").startAt(date).withSchedule(simpleSchedule().withIntervalInHours(168).repeatForever()).build(); // 168 hours in a week

        // schedule the job
        scheduler.scheduleJob(job, trigger);
    }

    public void start() throws SchedulerException {
        scheduler.start();
    }
    public void stop() throws SchedulerException {
        scheduler.shutdown();
    }
}

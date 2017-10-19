package corve.model;

import corve.notification.MailingCore;
import corve.notification.Notifier;
import corve.save.DBController;
import corve.setup.Settings;
import corve.util.Chore;
import corve.util.JobDataTags;
import corve.util.Room;
import org.apache.log4j.BasicConfigurator;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AssignerManager {

    private Scheduler scheduler;

    public AssignerManager(DBController db) throws SchedulerException {
        Notifier notifier = new MailingCore();
        List<Chore> chores = db.getChores();
        List<Room> rooms = db.getRooms();
        Collections.sort(rooms);

        BasicConfigurator.configure();

        scheduler = StdSchedulerFactory.getDefaultScheduler();

        // moment to assign
        LocalDateTime ldt = LocalDateTime.now().with(TemporalAdjusters.next(Settings.ASSIGN_DAY)).withHour(Settings.ASSIGN_HOUR).withMinute(0).withSecond(0).withNano(0);
        Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        // make the general job data which is used by all assigners
        JobDataMap jobData = new JobDataMap();
        jobData.put(JobDataTags.DATABASE, db);
        jobData.put(JobDataTags.NOTIFIER, notifier);
        jobData.put(JobDataTags.ROOMS, rooms);
        jobData.put(JobDataTags.CHORE, null); // don't know if this is necessary for .replace()

        for (Chore c : chores) {
            // make job data chore specific
            JobDataMap jobDataC = new JobDataMap(jobData);
            jobDataC.replace(JobDataTags.CHORE, c);

            JobDetail job = newJob(Assigner.class).setJobData(jobDataC).withIdentity(JobDataTags.ASSIGNER, "Job-" + c.getId() + "-" + c.getName()).build();

            Trigger trigger = newTrigger().withIdentity(JobDataTags.ASSIGNER, "Every 10 seconds" + c.getName()).startNow().withSchedule(simpleSchedule().withIntervalInSeconds(10 * c.getInterval()).repeatForever()).build(); // test trigger
//            Trigger trigger = newTrigger().withIdentity(JobDataTags.ASSIGNER, "Trigger-" + c.getId() + "-" + c.getName()).startAt(date).withSchedule(simpleSchedule().withIntervalInHours(168 * c.getInterval()).repeatForever()).build(); // 168 hours in a week

            // schedule the job
            scheduler.scheduleJob(job, trigger);
        }
    }

    public void start() throws SchedulerException {
        scheduler.start();
    }

    public void stop() throws SchedulerException {
        scheduler.shutdown();
    }
}

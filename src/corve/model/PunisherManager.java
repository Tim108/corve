package corve.model;

import corve.notification.MailingCore;
import corve.save.DBController;
import corve.setup.Settings;
import corve.util.JobDataTags;
import org.apache.log4j.BasicConfigurator;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class PunisherManager {

    private Scheduler scheduler;

    public PunisherManager(DBController db) throws SchedulerException {
        System.out.println("Creating punisher..");

        BasicConfigurator.configure();

        scheduler = StdSchedulerFactory.getDefaultScheduler();

        // moment to punish
        LocalDateTime ldt = LocalDateTime.now().with(TemporalAdjusters.next(Settings.PUNISH_DAY)).withHour(Settings.PUNISH_HOUR).withMinute(0).withSecond(0).withNano(0);
        Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        System.out.println("Creating job from punisher..");

        // make the general job data which is used by all assigners
        JobDataMap jobData = new JobDataMap();
        jobData.put(JobDataTags.DATABASE, db);
        jobData.put(JobDataTags.NOTIFIER, new MailingCore());

        JobDetail job = newJob(Punisher.class).setJobData(jobData).withIdentity("Job-Punisher").build();

//        Trigger trigger = newTrigger().withIdentity(JobDataTags.PUNISHER, "Every 10 seconds").startNow().withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever()).build(); // test trigger
        Trigger trigger = newTrigger().withIdentity(JobDataTags.PUNISHER, "Trigger").startAt(date).withSchedule(simpleSchedule().withIntervalInHours(168).repeatForever()).build(); // 168 hours in a week

        // schedule the job
        scheduler.scheduleJob(job, trigger);

        System.out.println("Creating punisher job done!");
    }

    public void start() throws SchedulerException {
        scheduler.start();
    }

    public void stop() throws SchedulerException {
        scheduler.shutdown();
    }
}

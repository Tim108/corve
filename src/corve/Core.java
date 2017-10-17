package corve;

import corve.model.Assigner;
import corve.model.Punisher;
import corve.model.SystemUpdater;
import org.apache.log4j.BasicConfigurator;
import corve.util.Chore;
import corve.util.Room;
import corve.util.Rooms;
import corve.mail.MailingCore;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import corve.save.DBController;

import java.util.*;

import static org.quartz.CronScheduleBuilder.monthlyOnDayAndHourAndMinute;
import static org.quartz.CronScheduleBuilder.weeklyOnDayAndHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by Tim on 23/11/2016.
 */
public class Core {
    /*

    let the assigner assign tasks each week on day x

    let the punisher punish people that didnt do their shit on day y

    meanwhile the core will have a method which can be called to tell the system a task is done, this will be called by a web interface so we can send links

    assignments, punishments and rewards will be notified by email

    make a shell around the whole system that makes sure input will not break the system

    make all info available on website

     */

    /*
    ff handig systeem bedenken hoe iets eens in de 4 weken gedaan kan worden.

    Dan kan daar direct in meegenomen worden dat als iets wat eens in de 4 weken gedaan moet worden en het word niet gedaan het na 1 week al moet
     */
    private static final String MAIL_USER_NAME = "corveesysteem@gmail.com";
    private static final String MAIL_PASSWORD = "39lA*v3a-V*ai(pal*a]s3j0Skla";

    private DBController dbc;
    private Assigner assignerWeek;
    private Assigner assigner4Week;
    private Punisher punisher;

    private MailingCore comm;

    private Scheduler scheduler;

    public Core() {
        dbc = new DBController();

        //load
        List<Room> rooms = dbc.getRooms();
        List<Chore> chores = dbc.getChores();

        //create assigner
        Map<Chore, Rooms> iteratorsWeek = new HashMap<>();
        Map<Chore, Rooms> iterators4Week = new HashMap<>();

        for (int i = 0; i < chores.size(); i++) {
            int start = (int) (Math.floor(rooms.size() / chores.size()) * i);
            Rooms rs = new Rooms(rooms, start);
            System.out.println(chores.get(i).getInterval());
            if (chores.get(i).getInterval() == 4) {
                iterators4Week.put(chores.get(i), rs);
            } else {
                iteratorsWeek.put(chores.get(i), rs);
            }
        }

        assignerWeek = new Assigner(iteratorsWeek, dbc);
        assigner4Week = new Assigner(iterators4Week, dbc);


        //create punisher
        punisher = new Punisher(dbc);

        //create laborer

        //create commcore
        comm = new MailingCore(MAIL_USER_NAME, MAIL_PASSWORD); // this should be in a file

        //create system updater
        try {
            BasicConfigurator.configure();
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDataMap jobDataWeek = new JobDataMap();
            jobDataWeek.put("assignerWeek", assignerWeek);
            jobDataWeek.put("punisher", punisher);
            JobDetail jobWeek = newJob(SystemUpdater.class).setJobData(jobDataWeek).withIdentity("SystemUpdaterWeek").build();
            Trigger triggerWeek = newTrigger().withIdentity("Every Saturday").startNow().withSchedule(weeklyOnDayAndHourAndMinute(DateBuilder.SATURDAY, 4, 0)).build(); //// TODO: 03/05/2017 dont forget this
            Trigger triggerSeconds = newTrigger().withIdentity("Every 10 seconds").startNow().withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();
            scheduler.scheduleJob(jobWeek, triggerSeconds);

            JobDataMap jobData4Week = new JobDataMap();
            jobData4Week.put("assigner4Week", assigner4Week);
            JobDetail job4Week = newJob(SystemUpdater.class).setJobData(jobData4Week).withIdentity("SystemUpdater4Week").build();
            Trigger trigger4Week = newTrigger().withIdentity("Every 4th Saturday").startNow().withSchedule(monthlyOnDayAndHourAndMinute(DateBuilder.SATURDAY, 4, 0)).build(); //// TODO: 03/05/2017 dont forget this
            Trigger triggerSeconds4 = newTrigger().withIdentity("Every 40 seconds").startNow().withSchedule(simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();
            scheduler.scheduleJob(job4Week, triggerSeconds4);
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

    public void stop() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}

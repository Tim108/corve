package corve.model;

import corve.notification.Notifier;
import corve.save.DBController;
import corve.util.JobDataTags;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.SQLException;

public class Punisher implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap data = jobExecutionContext.getJobDetail().getJobDataMap();
        DBController db = (DBController) data.get(JobDataTags.DATABASE);
        Notifier notifier = (Notifier) data.get(JobDataTags.NOTIFIER);
        try {
            db.punish();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Punishing done!");

        // theoretically, for the future
        notifier.notifyOfFine("", "", "");
        notifier.notifyOfReward("", "", "");
    }
}

package corve.model;

import corve.save.DBController;
import corve.util.JobDataTags;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.SQLException;

/**
 * Created by Tim on 07/04/2017.
 */
public class Punisher implements Job{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap data = jobExecutionContext.getJobDetail().getJobDataMap();
        DBController db = (DBController) data.get(JobDataTags.DATABASE);
        try {
            db.punish();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Punishing done!");
    }
}

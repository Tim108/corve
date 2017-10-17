package corve.model;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Tim on 21/02/2017.
 */
public class SystemUpdater implements Job {
    /**
     * gets the assigner and punisher from the jobdata and lets them assign / punish
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // assigner week
        Assigner assignerWeek = ((Assigner) jobExecutionContext.getJobDetail().getJobDataMap().get("assignerWeek"));
        if (assignerWeek != null)
            assignerWeek.assign();

        // assigner 4 weeks
        Assigner assigner4Week = ((Assigner) jobExecutionContext.getJobDetail().getJobDataMap().get("assigner4Week"));
        if (assigner4Week != null)
            assigner4Week.assign();

        Punisher punisher = ((Punisher) jobExecutionContext.getJobDetail().getJobDataMap().get("punisher"));
        if (punisher != null)
            punisher.punish();
    }
}

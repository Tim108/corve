import model.Assigner;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Tim on 21/02/2017.
 */
public class JobPretender implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Assigner assigner = ((Assigner) jobExecutionContext.getJobDetail().getJobDataMap().get("assigner"));
        if (assigner != null)
            assigner.assign();
    }
}

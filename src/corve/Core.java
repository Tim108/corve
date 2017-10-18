package corve;

import corve.mail.MailingCore;
import corve.model.AssignerManager;
import corve.model.Punisher;
import corve.model.PunisherManager;
import corve.save.DBController;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * Created by Tim on 23/11/2016.
 */
public class Core {
    private static final String MAIL_USER_NAME = "corveesysteem@gmail.com";
    private static final String MAIL_PASSWORD = "39lA*v3a-V*ai(pal*a]s3j0Skla";

    private DBController dbc;
    private AssignerManager am;
    private PunisherManager pm;
    private Punisher punisher;

    private MailingCore comm;

    private Scheduler scheduler;

    public Core() {
        dbc = new DBController();

        try {
            am = new AssignerManager(dbc);
            pm = new PunisherManager(dbc);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        comm = new MailingCore(MAIL_USER_NAME, MAIL_PASSWORD); // this should be in a file
    }

    public void start() {
        try {
            pm.start();
            am.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            pm.stop();
            am.stop();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}

package corve;

import corve.notification.MailingCore;
import corve.model.AssignerManager;
import corve.model.PunisherManager;
import corve.save.DBController;
import corve.util.Settings;
import org.quartz.SchedulerException;

/**
 * Created by Tim on 23/11/2016.
 */
public class Core {

    private DBController dbc;
    private AssignerManager am;
    private PunisherManager pm;

    private MailingCore comm;

    public Core() {
        dbc = new DBController();

        try {
            am = new AssignerManager(dbc);
            pm = new PunisherManager(dbc);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        comm = new MailingCore(Settings.MAIL_USER_NAME, Settings.MAIL_PASSWORD); // this should be in a file
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

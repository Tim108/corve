package corve;

import corve.setup.ConfigReader;
import corve.notification.MailingCore;
import corve.model.AssignerManager;
import corve.model.PunisherManager;
import corve.save.DBController;
import corve.setup.EmailTemplateReader;
import corve.setup.Settings;
import corve.util.EmailTemplates;
import org.quartz.SchedulerException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Tim on 23/11/2016.
 */
public class Core {

    private DBController dbc;
    private MailingCore mc;
    private AssignerManager am;
    private PunisherManager pm;

    public Core() {
        // check if config file is present and read it
        new ConfigReader("config.txt").readConfig();

        // make database -- we assume the database is correct
        dbc = new DBController();

        // read email templates
        new EmailTemplateReader("emailTemplates", dbc.getChores()).readTemplates();

        // make the managers
        try {
            am = new AssignerManager(dbc);
            pm = new PunisherManager(dbc);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

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

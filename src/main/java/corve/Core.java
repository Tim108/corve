package corve;

import corve.model.AssignerManager;
import corve.model.PunisherManager;
import corve.save.DBController;
import corve.setup.ConfigReader;
import corve.setup.EmailTemplateReader;
import org.quartz.SchedulerException;


public class Core implements Runnable{

    private AssignerManager am;
    private PunisherManager pm;

    public Core() {
        System.out.println("Creating Corve..");

        // check if config file is present and read it
        new ConfigReader("config.txt").readConfig();

        // make database -- we assume the database is correct
        DBController dbc = new DBController();

        // read email templates
        new EmailTemplateReader("emailTemplates", dbc.getChores()).readTemplates();

        // make the managers
        try {
            am = new AssignerManager(dbc);
            pm = new PunisherManager(dbc);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        System.out.println("Corve created!");
    }

    public void start() {
        System.out.println("Starting Corve..");
        try {
            pm.start();
            am.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        System.out.println("Corve started!");
    }

    public void stop() {
        System.out.println("Stopping Corve..");
        try {
            pm.stop();
            am.stop();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        System.out.println("Corve Stopped!");
    }

    @Override
    public void run() {
        start();
    }
}

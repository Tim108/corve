package corve;

import corve.setup.ConfigReader;
import corve.notification.MailingCore;
import corve.model.AssignerManager;
import corve.model.PunisherManager;
import corve.save.DBController;
import corve.setup.Settings;
import org.quartz.SchedulerException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Tim on 23/11/2016.
 */
public class Core {

    private DBController dbc;
    private AssignerManager am;
    private PunisherManager pm;

    public Core() {
        // check for file structure (settings file and folder with mail templates)
        File f = new File("config.txt");
        if(!f.exists() || f.isDirectory()) { // check the config file
            System.out.println("config.txt not present!");
            System.out.println("config file created, please fill in your configurations");
            try {
                FileWriter writer = new FileWriter(f);
                writer.write(ConfigReader.getEmptyConfig());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(1);
        }
        // get settings
        new ConfigReader().readConfig();

        // make database
        dbc = new DBController();

        // check database tables

        //
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

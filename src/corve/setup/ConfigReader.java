package corve.setup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.util.stream.Stream;

/**
 * Created by Tim on 18/10/2017.
 */
public class ConfigReader {
    String filepath;

    public ConfigReader(String filepath) {
        this.filepath = filepath;
        File f = new File(filepath);
        if(!f.exists() || f.isDirectory()) {
            System.out.println(filepath + " not present!");
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
    }

    public void readConfig() {
        try {
            Stream<String> lines = Files.lines(Paths.get(filepath));
            lines.forEach(s -> interpretLine(s));
            lines.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Config read!");
    }

    private void interpretLine(String line) {
        String[] parts = line.split("=", 2);
        if (parts.length != 2) return;

        String left = parts[0];
        String right = parts[1];

        if (right.equals("")) {
            System.out.println("WARNING: " + left + " not defined!");
        }

        switch (left) {
            case "db_url":
                Settings.DB_URL = right;
                break;
            case "db_username":
                Settings.DB_USERNAME = right;
                break;
            case "db_password":
                Settings.DB_PASSWORD = right;
                break;
            case "mail_username":
                Settings.MAIL_USERNAME = right;
                break;
            case "mail_password":
                Settings.MAIL_PASSWORD = right;
                break;
            case "assign_day":
                switch (right.toLowerCase()) {
                    case "monday":
                        Settings.ASSIGN_DAY = DayOfWeek.MONDAY;
                        break;
                    case "tuesday":
                        Settings.ASSIGN_DAY = DayOfWeek.TUESDAY;
                        break;
                    case "wednesday":
                        Settings.ASSIGN_DAY = DayOfWeek.WEDNESDAY;
                        break;
                    case "thursday":
                        Settings.ASSIGN_DAY = DayOfWeek.THURSDAY;
                        break;
                    case "friday":
                        Settings.ASSIGN_DAY = DayOfWeek.FRIDAY;
                        break;
                    case "saturday":
                        Settings.ASSIGN_DAY = DayOfWeek.SATURDAY;
                        break;
                    case "sunday":
                        Settings.ASSIGN_DAY = DayOfWeek.SUNDAY;
                }
                break;
            case "assign_hour":
                try {
                    Settings.ASSIGN_HOUR = Integer.parseInt(right);
                } catch (NumberFormatException e) {
                    if (!right.equals(""))
                        System.out.println("Entry for \'assign_hour\' not a number");
                }
                break;
            case "deadline_day":
                switch (right.toLowerCase()) {
                    case "monday":
                        Settings.DEADLINE_DAY = DayOfWeek.MONDAY;
                        break;
                    case "tuesday":
                        Settings.DEADLINE_DAY = DayOfWeek.TUESDAY;
                        break;
                    case "wednesday":
                        Settings.DEADLINE_DAY = DayOfWeek.WEDNESDAY;
                        break;
                    case "thursday":
                        Settings.DEADLINE_DAY = DayOfWeek.THURSDAY;
                        break;
                    case "friday":
                        Settings.DEADLINE_DAY = DayOfWeek.FRIDAY;
                        break;
                    case "saturday":
                        Settings.DEADLINE_DAY = DayOfWeek.SATURDAY;
                        break;
                    case "sunday":
                        Settings.DEADLINE_DAY = DayOfWeek.SUNDAY;
                }
                break;
            case "deadline_hour":
                try {
                    Settings.ASSIGN_HOUR = Integer.parseInt(right);
                } catch (NumberFormatException e) {
                    if (!right.equals(""))
                        System.out.println("Entry for \'assign_hour\' not a number");
                }
                break;
            case "punish_day":
                switch (right.toLowerCase()) {
                    case "monday":
                        Settings.PUNISH_DAY = DayOfWeek.MONDAY;
                        break;
                    case "tuesday":
                        Settings.PUNISH_DAY = DayOfWeek.TUESDAY;
                        break;
                    case "wednesday":
                        Settings.PUNISH_DAY = DayOfWeek.WEDNESDAY;
                        break;
                    case "thursday":
                        Settings.PUNISH_DAY = DayOfWeek.THURSDAY;
                        break;
                    case "friday":
                        Settings.PUNISH_DAY = DayOfWeek.FRIDAY;
                        break;
                    case "saturday":
                        Settings.PUNISH_DAY = DayOfWeek.SATURDAY;
                        break;
                    case "sunday":
                        Settings.PUNISH_DAY = DayOfWeek.SUNDAY;
                }
                break;
            case "punish_hour":
                try {
                    Settings.ASSIGN_HOUR = Integer.parseInt(right);
                } catch (NumberFormatException e) {
                    if (!right.equals(""))
                        System.out.println("Entry for \'assign_hour\' not a number");
                }
                break;
            default:
                return;
        }
    }

    public static String getEmptyConfig() {
        return "" +
                "db_url=\n" +
                "db_username=\n" +
                "db_password=\n" +
                "\n" +
                "mail_username=\n" +
                "mail_password=\n" +
                "\n" +
                "assign_day=\n" +
                "assign_hour=\n" +
                "deadline_day=\n" +
                "deadline_hour=\n" +
                "punish_day=\n" +
                "punish_hour=\n";
    }
}

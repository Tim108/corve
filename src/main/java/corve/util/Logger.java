package corve.util;

import corve.setup.Settings;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Logger {

    private String path;
    private String name;

    private List<String> msgs;


    public Logger(String name) {
        File logDir;
        if (Settings.LOG_FOLDER.equals("")) {
            logDir = new File("logs");
        } else {
            logDir = new File(Settings.LOG_FOLDER);
        }

        path = logDir.getPath();

        this.name = name;
        this.msgs = new ArrayList<>();

        if (!logDir.exists()) {
            System.out.println("creating directory: " + logDir.getName());
            boolean result = false;

            try {
                logDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                System.out.println("could not create log dir");
            }
            if (result) {
                System.out.println("log dir created");
            }
        }
    }

    public void log(String msg) {
        msgs.add(msg);
    }

    public void close() {
        try (FileWriter fw = new FileWriter(path + LocalDateTime.now().toString() + "_" + name, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for (String msg : msgs) {
                out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("logging failed -> " + LocalDateTime.now().toString() + "_" + name);
        }
    }
}

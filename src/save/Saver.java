package save;

import model.Room;
import model.Update;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tim on 2-12-16.
 */
public class Saver {
    private File folderWeekupdates;
    private File status;

    public Saver(String folderpath) {
        folderWeekupdates = new File(folderpath + "/weekupdates");
        if(!folderWeekupdates.exists()) folderWeekupdates.mkdir();
    }

    public void doWeekUpdate(Update update, List<Room> fined, List<Room> rewarded) {
        List<String> lines = new ArrayList<>();
        lines.add("Iteration no.: " + update.iteration);
        lines.add("Date: " + update.date);
        lines.add("Fined: " + update.fined);
        lines.add("Rewarded: " + update.rewarded);
        lines.add("Newly Assigned: " + update.assigned);
        lines.add("Total fined: " + fined.toString());
        lines.add("Total rewarded: " + rewarded.toString());

        Path file = Paths.get(folderWeekupdates.getPath() + "/" + folderWeekupdates.getName() + update.date);
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

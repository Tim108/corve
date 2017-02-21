import comm.CommCore;
import model.*;
import save.Loader;
import save.Saver;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

/**
 * Created by Tim on 23/11/2016.
 */
public class Core extends TimerTask {
    private Loader loader;
    private Saver saver;
    private State state;

    private CommCore comm;

    private Timer t;

    private List<Room> fined;
    private List<Room> rewarded;

    public Core(String folderpath) {
        //load
        loader = new Loader(folderpath);
        saver = new Saver(folderpath);

        List<Room> rooms = loader.getRooms();
        List<Chore> chores = loader.getChores();

        //create state
        Map<Chore, Rooms> iterators = new HashMap<>();

        for (int i = 0; i < chores.size(); i++) {
            int start = (int) (Math.floor(rooms.size() / chores.size()) * i);
            Rooms rs = new Rooms(rooms, start);
            iterators.put(chores.get(i), rs);
        }

        state = new State(iterators);

        //create commcore
        comm = new CommCore(loader.getGmailUsername(), loader.getGmailPassword()); // this should be in a file

        //instantiate fined and rewarded totals
        fined = new ArrayList<>();
        rewarded = new ArrayList<>();
    }

    public void start() {
        t = new Timer();
        t.schedule(this, new Date(), 30000); // the long should be 604800000 (one week)
    }

    public void run() {
        //update system
        Map<Chore, String> choresDone = comm.readmails();
        for (Chore c : choresDone.keySet()) {
            Optional<Room> or = loader.getRooms().stream().filter(room -> room.getEmail().equals(choresDone.get(c))).findAny();
            if (or.isPresent()) {
                state.done(c, or.get());
            }
        }
        state.update();
        /*
        Update u = state.update();
        System.out.println(u);
        fined.addAll(u.fined);
        rewarded.addAll(u.rewarded);

        //save results
        saver.doWeekUpdate(u, fined, rewarded);

        //send emails
        comm.fine(u.fined);
        comm.reward(u.rewarded);
        comm.assign(u.assigned);
        */

    }

    public void stop() {
        t.cancel();
    }

    private String selectfile(String title) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println(chooser.getSelectedFile().getPath());
            return chooser.getSelectedFile().getPath();
        }
        System.exit(0);
        return null;
    }
}

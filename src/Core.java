import comm.CommCore;
import model.*;
import save.Loader;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

/**
 * Created by Tim on 23/11/2016.
 */
public class Core extends TimerTask{
    private List<Room> rooms;
    private List<Chore> chores;

    private State state;
    private CommCore comm;
    private Timer t;

    public Core(String folderpath) {
        //core should get these as arguments
//        String roomsPath = selectfile("Select rooms folder");
//        String choresPath = selectfile("Select chores folder");

        String roomsPath = folderpath + "/rooms";
        String choresPath = folderpath + "/chores";

        //load
        Loader l = new Loader();

        rooms = l.loadRooms(roomsPath);
        chores = l.loadChores(choresPath);

        //create state
        Map<Chore, Rooms> iterators = new HashMap<>();

        for (int i = 0; i < chores.size(); i++) {
            int start = (int) (Math.floor(rooms.size() / chores.size()) * i);
            Rooms rs = new Rooms(rooms, start);
            iterators.put(chores.get(i), rs);
        }

        state = new State(iterators);

        //create commcore
        comm = new CommCore("corveesysteem@gmail.com", "39lA*v3a-V*ai(pal*a]s3j0Skla"); // this should be in a file


    }

    public void start() {
        t = new Timer();
        t.schedule(this, new Date(), 30000); // the long should be 604800000 (one week)
    }

    public void run(){
        //update system
        Map<Chore, String> choresDone = comm.readmails();
        for (Chore c : choresDone.keySet()) {
            Optional<Room> or = rooms.stream().filter(room -> room.getEmail() .equals( choresDone.get(c))).findAny();
            if (or.isPresent()) {
                state.done(c, or.get());
            }
        }

        Update u = state.update();
        System.out.println(u);

        //send emails
        comm.fine(u.fined);
        comm.reward(u.rewarded);
        comm.assign(u.assigned);
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

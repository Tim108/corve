package save;

import model.Chore;
import model.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Tim on 24/11/2016.
 */
public class Loader {
    private List<Room> rooms;
    private List<Chore> chores;

    private String gmailUsername;
    private String gmailPassword;

    public Loader(String folderpath){
        rooms = loadRooms(folderpath + "/rooms");
        chores = loadChores(folderpath + "/chores");
        loadCredentials(folderpath);
        System.out.println("$" + getGmailUsername() + "$");
        System.out.println("$" + getGmailPassword() + "$");
    }

    private List<Room> loadRooms(String folderpath) {
        File folder = new File(folderpath);
        File[] listOfFiles = folder.listFiles();

        List<Room> rooms = new ArrayList<>();

        for (File f : listOfFiles) {
            String name;
            int number;
            String email = "";

            String filename = f.getName();
            if (filename.contains(".")) {
                filename = filename.substring(0, filename.lastIndexOf("."));
            }

            name = filename.split("#")[0];
            number = Integer.parseInt(filename.split("#")[1]);

            try {
                Scanner s = new Scanner(f).useDelimiter("\\Z");
                if(s.hasNext()) email = s.next();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            rooms.add(new Room(number, name, email));
        }

        return rooms;
    }

    private List<Chore> loadChores(String folderpath) {
        File folder = new File(folderpath);
        File[] listOfFiles = folder.listFiles();

        List<Chore> chores = new ArrayList<>();

        for (File f : listOfFiles) {
            String name;
            int interval;
            String description = "";

            String filename = f.getName();
            if (filename.contains(".")) {
                filename = filename.substring(0, filename.lastIndexOf("."));
            }

            name = filename.split("#")[0];
            interval = Integer.parseInt(filename.split("#")[1]);

            try {
                Scanner s = new Scanner(f).useDelimiter("\\Z");
                if(s.hasNext()) description = s.next();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            chores.add(new Chore(name, description, interval));
        }

        return chores;
    }

    private void loadCredentials(String folderpath){
        File folder = new File(folderpath);
        File[] listOfFiles = folder.listFiles();

        Optional<File> of = Arrays.stream(listOfFiles).filter(file -> file.getName().contains("gmail")).findAny();
        if (of.isPresent()) {
            File f = of.get();
            Scanner s = null;
            try {
                s = new Scanner(f);
                if(s.hasNext()) gmailUsername = s.next().replace("\n", "");
                if(s.hasNext()) gmailPassword = s.next().replace("\n", "");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Chore> getChores() {
        return chores;
    }

    public String getGmailUsername() {
        return gmailUsername;
    }

    public String getGmailPassword() {
        return gmailPassword;
    }
}

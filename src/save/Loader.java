package save;

import model.Chore;
import model.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Tim on 24/11/2016.
 */
public class Loader {

    public List<Room> loadRooms(String folderpath) {
        File folder = new File(folderpath);
        File[] listOfFiles = folder.listFiles();

        List<Room> rooms = new ArrayList<>();

        for (File f : listOfFiles) {
            String name;
            int number;
            String email = "";

            String filename = f.getName();
            filename = filename.substring(0, filename.lastIndexOf("."));

            name = filename.split("#")[0];
            number = Integer.parseInt(filename.split("#")[1]);

            try {
                email = new Scanner(new File(folderpath + "/" + f.getName())).useDelimiter("\\Z").next();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            rooms.add(new Room(number, name, email));
        }

        return rooms;
    }

    public List<Chore> loadChores(String folderpath) {
        File folder = new File(folderpath);
        File[] listOfFiles = folder.listFiles();

        List<Chore> chores = new ArrayList<>();

        for (File f : listOfFiles) {
            String name;
            int interval;
            String description = "";

            String filename = f.getName();
            filename = filename.substring(0, filename.lastIndexOf("."));

            name = filename.split("#")[0];
            interval = Integer.parseInt(filename.split("#")[1]);


            try {
                description = new Scanner(new File(folderpath + "/" + f.getName())).useDelimiter("\\Z").next();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            chores.add(new Chore(name, description, interval));
        }

        return chores;
    }
}

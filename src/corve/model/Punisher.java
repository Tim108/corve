package corve.model;

import corve.save.DBController;

import java.sql.SQLException;

/**
 * Created by Tim on 07/04/2017.
 */
public class Punisher {
    private DBController db;

    public Punisher(DBController db) {
        this.db = db;
    }

    /**
     * takes records from the record queue until it finds one with an end that that is still to come
     * these records will remain in the database without a room_done_id, effectively marking them failed
     */
    public void punish(){
        try {
            db.punish();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Punishing done!");
    }
}

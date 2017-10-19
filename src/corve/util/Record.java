package corve.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;


public class Record {
    private int id = -1;
    private int room_id;
    private int chore_id;
    private Timestamp start_date;
    private Timestamp end_date;
    private int done_room_id = -1;
    private String code;


    public Record(int room_id, int chore_id, Timestamp start_date, Timestamp end_date) {
        this.room_id = room_id;
        this.chore_id = chore_id;
        this.start_date = start_date;
        this.end_date = end_date;
        SecureRandom random = new SecureRandom();
        String identifier = new BigInteger(130, random).toString(32);
        code = "r" + room_id + "c" + chore_id + "t" + end_date + "i" + identifier;
    }

    public int getId() {
        return id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public int getChore_id() {
        return chore_id;
    }

    public Timestamp getStart_date() {
        return start_date;
    }

    public Timestamp getEnd_date() {
        return end_date;
    }

    public int getDone_room_id() {
        return done_room_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", room_id=" + room_id +
                ", chore_id=" + chore_id +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", done_room_id=" + done_room_id +
                ", code=" + code +
                '}';
    }

}

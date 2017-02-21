package save;

import java.sql.Timestamp;

/**
 * Created by Tim on 21/02/2017.
 */
public class Record {
    private int id = -1;
    private int room_id;
    private int chore_id;
    private Timestamp start_date;
    private Timestamp end_date;
    private int done_room_id = -1;

    public Record(int room_id, int chore_id, Timestamp start_date, Timestamp end_date) {
        this.room_id = room_id;
        this.chore_id = chore_id;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Record(int id, int room_id, int chore_id, Timestamp start_date, Timestamp end_date, int done_room_id) {
        this.id = id;
        this.room_id = room_id;
        this.chore_id = chore_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.done_room_id = done_room_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getChore_id() {
        return chore_id;
    }

    public void setChore_id(int chore_id) {
        this.chore_id = chore_id;
    }

    public Timestamp getStart_date() {
        return start_date;
    }

    public void setStart_date(Timestamp start_date) {
        this.start_date = start_date;
    }

    public Timestamp getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Timestamp end_date) {
        this.end_date = end_date;
    }

    public int getDone_room_id() {
        return done_room_id;
    }

    public void setDone_room_id(int done_room_id) {
        this.done_room_id = done_room_id;
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
                '}';
    }
}

package save;

import model.Chore;
import model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim on 20/02/2017.
 */
public class DBController {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    Connection conn;

    public void addRecords(List<Record> records) {
        openConnection();
        records.forEach(this::addRecord);
        closeConnection();
    }

    public void addRecord(Record record) {
        boolean openedBySelf = false;
        try {
            if (conn == null || conn.isClosed()) {
                openConnection();
                openedBySelf = true;
            }
            PreparedStatement ps = conn.prepareStatement("INSERT INTO corve.records (room_id, chore_id, start_date, end_date, done_room_id) VALUES (?,?,?,?,?)");
            if (record.getId() != -1) {
                ps = conn.prepareStatement("INSERT INTO corve.records (room_id, chore_id, start_date, end_date, done_room_id, id) VALUES (?,?,?,?,?,?)");
                ps.setInt(6, record.getId());
            }
            ps.setInt(1, record.getRoom_id());
            ps.setInt(2, record.getChore_id());
            ps.setTimestamp(3, record.getStart_date());
            ps.setTimestamp(4, record.getEnd_date());
            ps.setInt(5, record.getDone_room_id());
            ps.execute();
            ps.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (openedBySelf) {
                closeConnection();
            }
        }
    }

    public List<Record> getRunningRecords() {
        List<Record> records = new ArrayList<>();
        try {
            openConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM corve.records WHERE end_date > CURRENT_TIMESTAMP AND done_room_id=-1 ");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int room_id = rs.getInt("room_id");
                int chore_id = rs.getInt("chore_id");
                Timestamp start_date = rs.getTimestamp("start_date");
                Timestamp end_date = rs.getTimestamp("end_date");
                int done_room_id = rs.getInt("done_room_id");
                records.add(new Record(id, room_id, chore_id, start_date, end_date, done_room_id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return records;
    }

    public void endRecord(int id, int done_room_id) {
        try {
            openConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE corve.records SET done_room_id=? WHERE id=? AND end_date > CURRENT_TIMESTAMP ");
            ps.setInt(1, done_room_id);
            ps.setInt(2, id);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public List<Room> getRooms(){
        List<Room> rooms = new ArrayList<>();
        try {
            openConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM corve.rooms");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                rooms.add(new Room(id, name, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return rooms;
    }

    public List<Chore> getChores() {
        List<Chore> chores = new ArrayList<>();
        try {
            openConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM corve.chores");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int choreinterval = rs.getInt("choreinterval");
                chores.add(new Chore(id, name, description, choreinterval));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return chores;
    }

    private void openConnection() {
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            //it's fine
        }
    }
}

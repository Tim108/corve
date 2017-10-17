package corve.save;

import corve.util.Chore;
import corve.util.Record;
import corve.util.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim on 20/02/2017.
 */
public class DBController {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/corve?verifyServerCertificate=false&useSSL=true";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    Connection conn;

    public DBController() {
        openConnection();
    }

    @Override
    protected void finalize() throws Throwable {
        closeConnection();
        super.finalize();
    }

    /**
     * adds the given record to the database
     *
     * @param record
     */
    public void addRecord(Record record) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO records (room_id, chore_id, start_date, end_date, done_room_id, code) VALUES (?,?,?,?,?,?)");
            if (record.getId() != -1) {
                ps = conn.prepareStatement("INSERT INTO records (room_id, chore_id, start_date, end_date, done_room_id, code, id) VALUES (?,?,?,?,?,?,?)");
                ps.setInt(7, record.getId());
            }
            ps.setInt(1, record.getRoom_id());
            ps.setInt(2, record.getChore_id());
            ps.setTimestamp(3, record.getStart_date());
            ps.setTimestamp(4, record.getEnd_date());
            ps.setInt(5, record.getDone_room_id());
            ps.setString(6, record.getCode());
            ps.execute();
            ps.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void punish()
            throws SQLException {
        PreparedStatement ps1 = conn.prepareStatement("set @N := (now());");
        PreparedStatement ps2 = conn.prepareStatement("INSERT INTO settlements(room_id, record_id, amount) SELECT x.id, x.room_id, 10 FROM (SELECT * FROM records WHERE @N > records.end_date AND records.done_room_id = -1) x ;");
        PreparedStatement ps3 = conn.prepareStatement("INSERT INTO archive select * FROM records where records.end_date < @N;");
        PreparedStatement ps4 = conn.prepareStatement("DELETE FROM records where records.end_date < @N;");

        try {
            conn.setAutoCommit(false);
            ps1.execute();
            ps2.execute();
            ps3.execute();
            ps4.execute();
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            if (ps1 != null) {
                ps1.close();
            }
            if (ps2 != null) {
                ps2.close();
            }
            if (ps3 != null) {
                ps3.close();
            }
            if (ps4 != null) {
                ps4.close();
            }
            conn.setAutoCommit(true);
        }
    }


    /**
     * returns all rooms from the database
     *
     * @return
     */
    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM rooms");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                rooms.add(new Room(id, name, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    /**
     * returns all chores from the database
     *
     * @return
     */
    public List<Chore> getChores() {
        List<Chore> chores = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM chores");
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
        }
        return chores;
    }

    /**
     * opens the database connection
     */
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

    /**
     * closes the database connections
     */
    private void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            //it's fine
        }
    }
}

import save.DBController;
import save.Record;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("arg0 = corve folder");
//            System.exit(0);
//        }
//
//        Core c = new Core(args[0]);
//        c.start();
        DBController dbc = new DBController();
        dbc.addRecord(new Record(4, 8, Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plus(Duration.ofMinutes(10)))));
        dbc.getRunningRecords().forEach(System.out::println);
        dbc.endRecord(17, 8);
        System.out.println("===================");
        dbc.getRunningRecords().forEach(System.out::println);
    }


}

import save.DBController;
import save.Record;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.TemporalAdjusters;

public class Main {

    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("arg0 = corve folder");
//            System.exit(0);
//        }
//
//        Core c = new Core(args[0]);
//        c.start();
//        DBController dbc = new DBController();
//        dbc.addRecord(new Record(1,1, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)))));
//        dbc.getRunningRecords().forEach(System.out::println);
//        dbc.endRecord(17, 8);
//        System.out.println("===================");
//        dbc.getRunningRecords().forEach(System.out::println);

        Core c = new Core();
        c.start();
    }


}

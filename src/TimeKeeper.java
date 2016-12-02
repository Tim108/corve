import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tim on 25/11/2016.
 */
public class TimeKeeper implements Runnable {
    Core core;
    Date start;

    public TimeKeeper(Core core, Date start) {
        this.core = core;
        this.start = start;
    }

    @Override
    public void run() {
        Timer t = new Timer();
        t.schedule(core, start, 3000);
    }
}

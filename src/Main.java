import comm.CommOut;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("arg0 = corve folder");
            System.exit(0);
        }

        Core c = new Core(args[0]);
        c.start();
    }


}

import corve.Core;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.print("Type stop to stop the system and exit");

        Core c = new Core();
        Thread t = new Thread(c);
        t.start();

        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            if (sc.nextLine().equals("stop")) {
                c.stop();
                break;
            }
        }
    }


}

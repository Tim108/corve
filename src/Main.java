import corve.Core;

public class Main {

    public static void main(String[] args) {
        Core c = new Core();
        Thread t = new Thread(c);
        t.start();

        boolean stop = false;
        while (!stop) {
            System.out.print("Type stop to stop the system and exit");
            String input = System.console().readLine();
            if (input.equals("stop")) {
                stop = true;
            }
        }

        c.stop();
    }


}

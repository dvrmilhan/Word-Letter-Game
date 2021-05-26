import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    Timer timer;

    public GameTimer(int seconds) {

        System.out.println("TIME IS START!");
        timer = new Timer();
        timer.schedule(task, seconds * 1000);
    }

    TimerTask task = new TimerTask(){

        public void run() {
            System.out.println("TIME İS UP! YOU LOST.");
            timer.cancel();
        }

    };
}

package timing;

import java.util.TimerTask;
import java.util.Timer;

public class GameTimer {

    public Timer timer = new Timer();
    public boolean started = false;
    public int delay = 0;
    public int period = 1000;
    public int interval = 600;
    public boolean paused;
    public int minutes;
    public int seconds;
    String formattedSeconds;
    String formattedMinutes;
    public boolean isOver = false;

    public void start() {
        timer.schedule(new TimerTask() {

            public void run() {
                setInterval();
            }
        }, delay, period);
    }

    public int setInterval() {
        if (interval == 0) {
            isOver = true;
            timer.cancel();
        }
        if (!paused)
            interval--;

        return interval;
    }

    public void pause() {

        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public String getTime() {
        seconds = (int)interval % 60;
        minutes = (int)interval / 60;
        formattedSeconds = String.format("%02d", seconds);
        formattedMinutes = String.format("%02d", minutes);
        return (formattedMinutes + " : " + formattedSeconds);
    }

    public void reset() {
        interval = 600;
    }

    public void increment() {
        interval += 5;
    }
}


package timing;

import java.util.TimerTask;
import java.util.Timer;

public class GameTimer {

    /*
    public boolean started;
    public double startTime;
    public double currentTime;
    public double pauseTime;
    public double pauseDuration;
    public int minutes;
    public int seconds;
    public boolean paused = false;

    public void start() {
        startTime = System.nanoTime();
    }

    public String getTime() {
        if (paused) {
            pauseDuration += System.nanoTime() - pauseTime;
        }
        pauseDuration /= 1000000000;
        currentTime = System.nanoTime() - pauseDuration;
        currentTime = (currentTime - startTime) / 1000000000;

        seconds = (int)currentTime % 60;
        minutes = (int)currentTime / 60;
        return minutes + " : " + seconds;
    }

    public void pause() {
        paused = true;
        pauseTime = currentTime;
    }

    public void resume() {
        paused = false;
    }
     */


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


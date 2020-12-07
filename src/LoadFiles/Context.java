package LoadFiles;

import java.util.ArrayList;

public class Context {
    private LoadStrategy loadStrategy;

    public Context(LoadStrategy loadStrategy) {
        this.loadStrategy = loadStrategy;
    }

    public ArrayList<ArrayList<String>> executeLoadStrategy(String fileName) {
        return loadStrategy.loadGame(fileName);
    }
}

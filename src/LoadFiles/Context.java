/*
 * Wes Beard - wesley.beard@mymail.champlain.edu
 * Michael Leonard - michael.leonard@mymail.chamamplain.edu
 * CSI-340 Final Project
 * 12/7/2020
 *
 * Written by Michael Leonard
 *
 * This file executes a given strategy
 */

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

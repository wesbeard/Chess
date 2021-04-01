/*
 * Wes Beard - wesley.beard@mymail.champlain.edu
 * Michael Leonard - michael.leonard@mymail.chamamplain.edu
 * CSI-340 Final Project
 * 12/7/2020
 *
 * Written by Michael Leonard
 *
 * This file loads a stored game file into the context
 */

package LoadFiles;

import java.util.ArrayList;

public interface LoadStrategy {
    public ArrayList<ArrayList<String>> loadGame(String fileLocation);
}

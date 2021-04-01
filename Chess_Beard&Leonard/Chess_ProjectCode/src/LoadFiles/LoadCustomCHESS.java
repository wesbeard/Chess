/*
 * Wes Beard - wesley.beard@mymail.champlain.edu
 * Michael Leonard - michael.leonard@mymail.chamamplain.edu
 * CSI-340 Final Project
 * 12/7/2020
 *
 * Written by Michael Leonard
 *
 * This file loads a custom chess format and adds it to the
 * replay functionality to replay in game
 */

package LoadFiles;

import Command.BoardReplay;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadCustomCHESS implements LoadStrategy {
    @Override
    public ArrayList<ArrayList<String>> loadGame(String fileLocation) {
        BoardReplay.fileReplayStarted = true;
        ArrayList<ArrayList<String>> entireGame = new ArrayList<ArrayList<String>>();

        // read all data from file
        FileReader fr = null;
        try {
            fr = new FileReader(fileLocation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int i = 0;
        String allData = "";
        while (true) {
            try {
                if (!((i=fr.read()) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            allData += (char)i;
        }

        try {
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] allLines = allData.split("\\n");
        for (String line : allLines) {
            String[] allPieceStrings = line.split(" ");
            ArrayList<String> oneBoard = new ArrayList<String>();
            for (String pieceString : allPieceStrings) {
                oneBoard.add(pieceString);
            }
            entireGame.add(oneBoard);
        }

        return entireGame;
    }
}

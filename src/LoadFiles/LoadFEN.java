package LoadFiles;

import java.io.*;

import java.util.ArrayList;

public class LoadFEN implements LoadStrategy {
    @Override
    public ArrayList<ArrayList<String>> loadGame(String fileLocation) {
        // pass the path to the file as a parameter

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

        // turning FEN into our format vvv

        String[] lineSeperated = allData.split("\n");

        for (String line : lineSeperated) {
            ArrayList<String> ourGameFormat = new ArrayList<String>();
            String[] result = line.split("/");
            for (int rankNum = 0; rankNum < result.length; rankNum++) {
                String FENsegment = result[rankNum];
                for (int fileNum = 0; fileNum < 8; fileNum++) {
                    char c = FENsegment.charAt(0);
                    FENsegment = FENsegment.substring(1);

                    if(Character.isDigit(c))
                    {
                        fileNum += Character.getNumericValue(c)-1;
                    } else {

                        String pieceType = "";
                        switch (c) {
                            case 'P':
                                pieceType = "PL";
                                break;
                            case 'p':
                                pieceType = "PD";
                                break;
                            case 'R':
                                pieceType = "RL";
                                break;
                            case 'r':
                                pieceType = "RD";
                                break;
                            case 'N':
                                pieceType = "NL";
                                break;
                            case 'n':
                                pieceType = "ND";
                                break;
                            case 'B':
                                pieceType = "BL";
                                break;
                            case 'b':
                                pieceType = "BD";
                                break;
                            case 'Q':
                                pieceType = "QL";
                                break;
                            case 'q':
                                pieceType = "QD";
                                break;
                            case 'K':
                                pieceType = "KL";
                                break;
                            case 'k':
                                pieceType = "KD";
                                break;
                        }

                        ourGameFormat.add(pieceType + Integer.toString(fileNum) + Integer.toString(rankNum));
                    }
                }
            }

            entireGame.add(ourGameFormat);
        }

        return entireGame;
    }
}

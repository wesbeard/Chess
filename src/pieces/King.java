/*
 * Wes Beard - wesley.beard@mymail.champlain.edu
 * Michael Leonard - michael.leonard@mymail.chamamplain.edu
 * CSI-340 Final Project
 * 12/7/2020
 *
 * Written by Wes Beard
 *
 * This file contains the code specific to the king pieces,
 * including the unique movement, checking, and castling procedures
 */


package pieces;

import main.Util;
import processing.sound.SoundFile;
import java.util.ArrayList;
import java.util.Map;

import Command.Command;
import static main.Constants.*;

public class King extends Piece implements Command {

    public King (String sideColor, int x, int y) {
        super.side = sideColor;
        super.type = "K";
        super.y = y;
        super.x = x;

        if (side == "dark") {
            super.shapeFile = "images/piecesets/" + super.pieceSet + "/b" + type + ".svg";
        }
        else {
            super.shapeFile = "images/piecesets/" + super.pieceSet + "/w" + type + ".svg";
        }
    }

    public boolean move(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake, ArrayList<Piece> lostPieces) {

        boolean castled = false;

        // Check for castle
        // If dark and not moved then check which side is being castled and pass ending coordinates
        if (side == "dark" && !moved) {
            if((targetX == 1 && targetY == 0) && !blockedHorizontal(1, 0, pieces, toTake)) {
                castled = castle(pieces,1, 0, 0, 0, 2, 0, CASTLESOUND);
            }
            else if((targetX == 6 && targetY == 0) && !blockedHorizontal(6, 0, pieces, toTake)) {
                castled = castle(pieces, 6, 0, 7, 0, 5, 0, CASTLESOUND);
            }
        }
        // If light and not moved then check which side is being castled and pass ending coordinates
        else if (side == "light" && !moved) {
            if((targetX == 1 && targetY == 7) && !blockedHorizontal(1, 7, pieces, toTake)) {
                castled = castle(pieces, 1, 7, 0, 7, 2, 7, CASTLESOUND);
            }
            else if((targetX == 6 && targetY == 7) && !blockedHorizontal(6, 7, pieces, toTake)) {
                castled = castle(pieces, 6, 7, 7, 7, 5, 7, CASTLESOUND);
            }
        }

        // Normal movement if not attempting castle
        if(((abs(x - targetX) <= 1) && (abs(y - targetY) <= 1)) && !castled && !isPinned(pieces, targetX, targetY, toTake)) {
            if (toTake != null) {
                TAKESOUND.play();
            }
            else {
                MOVESOUND.play();
            }
            super.x = targetX;
            super.y = targetY;
            if (isCheck(pieces, toTake)) {
                CHECKSOUND.play();
            }
            take(pieces,toTake, lostPieces);
            moved = true;
            return true;
        }

        // If castled then move is true
        if(castled) {
            return true;
        }
        // If not and others fail then move is false
        else{
            System.out.println("Invalid Move: K" + Util.convertRank(targetX) + Util.convertFile(targetY));
            INVALIDSOUND.play();
            return false;
        }
    }

    @Override
    public boolean isCheck(ArrayList<Piece> pieces, Piece toTake) {
        return false;
    }

    @Override
    public Map<Integer, Integer> possibleMoves(ArrayList<Piece> pieces) {
        return null;
    }

    public boolean castle(ArrayList<Piece> pieces, int castleX, int castleY, int rookX, int rookY, int rookX2, int rookY2, SoundFile castlSound) {
        for (Piece piece : pieces) {
            if((piece.x == rookX && piece.y == rookY) && !piece.moved){
                castlSound.play();
                //Move king to castle position
                x = castleX;
                y = castleY;
                moved = true;
                //Move rook to castle position
                piece.x = rookX2;
                piece.y = rookY2;
                piece.moved = true;
                return true;
            }
        }
        return false;
    }
}

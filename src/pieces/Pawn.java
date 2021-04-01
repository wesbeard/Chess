/*
 * Wes Beard - wesley.beard@mymail.champlain.edu
 * Michael Leonard - michael.leonard@mymail.chamamplain.edu
 * CSI-340 Final Project
 * 12/7/2020
 *
 * Written by Wes Beard
 *
 * This file contains the code specific to the bishop pieces,
 * including the unique movement, checking, and promoting procedures
 */

package pieces;

import Command.Command;
import main.Util;
import java.util.ArrayList;
import static main.Constants.*;

public class Pawn extends Piece implements Command {

    public Pawn (String sideColor, int x, int y) {
        super.side = sideColor;
        super.type = "P";
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
        boolean pinned = isPinned(pieces, targetX, targetY, toTake);

        if (enPassant(targetX, targetY, pieces, toTake, lostPieces) && !pinned && toTake == null) {
            MOVESOUND.play();
            super.x = targetX;
            super.y = targetY;
            if (isCheck(pieces, toTake)) {
                CHECKSOUND.play();
            }
            moved = true;
            if(y == 0) {
                promote(pieces);
            }
            return true;
        }
        else if (targetX == x && !pinned) {
            if(toTake != null){
                if(toTake.x == x){
                    return false;
                }
            }
            else if (side == "light") {
                if(targetY == y - 1 || (targetY == y - 2 && !moved)) {
                    if (!moved && targetY == y - 2) {
                        canEnPassant = true;
                    }
                    else {
                        canEnPassant = false;
                    }
                    MOVESOUND.play();
                    super.x = targetX;
                    super.y = targetY;
                    if (isCheck(pieces, toTake)) {
                        CHECKSOUND.play();
                    }
                    moved = true;
                    if(y == 0) {
                        promote(pieces);
                    }
                    return true;
                }
            }
            else {
                if(targetY == y + 1 || (targetY == y + 2 && !moved)) {
                    if (!moved && targetY == y + 2) {
                        canEnPassant = true;
                    }
                    else {
                        canEnPassant = false;
                    }
                    MOVESOUND.play();
                    super.x = targetX;
                    super.y = targetY;
                    if (isCheck(pieces, toTake)) {
                        CHECKSOUND.play();
                    }
                    moved = true;
                    if(y == 7) {
                        promote(pieces);
                    }
                    return true;
                }
            }
        }
        else if ((targetX == x + 1 || targetX == x - 1) && toTake != null && !pinned) {
            if (side == "light") {
                if (targetY == y - 1) {
                    TAKESOUND.play();
                    super.x = targetX;
                    super.y = targetY;
                    if (isCheck(pieces, toTake)) {
                        CHECKSOUND.play();
                    }
                    take(pieces,toTake, lostPieces);
                    moved = true;
                    if(y == 0) {
                        promote(pieces);
                    }
                    return true;
                }
            }
            else {
                if (targetY == y + 1) {
                    TAKESOUND.play();
                    super.x = targetX;
                    super.y = targetY;
                    if (isCheck(pieces, toTake)) {
                        CHECKSOUND.play();
                    }
                    take(pieces,toTake, lostPieces);
                    moved = true;
                    if(y == 7) {
                        promote(pieces);
                    }
                    return true;
                }
            }
        }
        System.out.println("Invalid Move P" + Util.convertRank(targetX) + Util.convertFile(targetY));
        INVALIDSOUND.play();
        return false;
    }

    public void promote(ArrayList<Piece> pieces) {
        if (side == "light") {
            pieces.add(PieceFactory.getPiece("Q","light", x, y));
        }
        else {
            pieces.add(PieceFactory.getPiece("Q","dark", x, y));
        }

        pieces.remove(this);
    }

    @Override
    public boolean isCheck(ArrayList<Piece> pieces, Piece toTake) {

        Piece enemyKing = getKing(pieces, side);

        if (enemyKing != null) {
            if (enemyKing.x == x + 1 || enemyKing.x == x - 1) {
                if (side == "light") {
                    if (enemyKing.y == y - 1) {
                        System.out.println("Check by " + side + " Pawn!");
                        return true;
                    }
                } else {
                    if (enemyKing.y == y + 1) {
                        System.out.println("Check by " + side + " Pawn!");
                        return true;
                    }
                }
            }
        }

        return false;
    }
}

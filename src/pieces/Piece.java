/*
 * Wes Beard - wesley.beard@mymail.champlain.edu
 * Michael Leonard - michael.leonard@mymail.chamamplain.edu
 * CSI-340 Final Project
 * 12/7/2020
 *
 * Written by Wes Beard
 *
 * This file contains the piece code for functionality
 * shared by every piece type such as checking pinned,
 * checking the king, taking a piece, and other various
 * common code which cuts down on code duplication
 */

package pieces;

import processing.core.*;
import java.util.ArrayList;
import Command.Command;

public abstract class Piece extends PApplet implements Command {

    public String side;
    public String type;
    public int x;
    public int y;
    public String shapeFile;
    public PShape shape;
    public String pieceSet = "tatiana";
    public boolean moved = false;
    public boolean canEnPassant = false;

    public abstract boolean move(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake, ArrayList<Piece> lostPieces);

    public abstract boolean isCheck(ArrayList<Piece> pieces, Piece toTake);

    public boolean isCheckmate(ArrayList<Piece> pieces, int targetX, int targetY) {
        /* WIP for testing purposes */
        return false;
    }

    // This could be moved to Pawn but needs to be here for testing
    public boolean enPassant(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake, ArrayList<Piece> lostPieces) {
        if (toTake != null || isPinned(pieces, targetX, targetY, toTake)) {
            return false;
        }
        for (Piece piece : pieces) {
            if (side == "light") {
                if (piece.side == "dark" && piece.canEnPassant && piece.type == "P" && piece.y == targetY + 1) {
                    take(pieces, piece, lostPieces);
                    return true;
                }
            }
            else {
                if (piece.side == "light" && piece.canEnPassant && piece.type == "P" && piece.y == targetY - 1) {
                    take(pieces, piece, lostPieces);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPinned(ArrayList<Piece> pieces, int targetX, int targetY, Piece toTake) {
         int originalX = x;
         int originalY = y;
         x = targetX;
         y = targetY;

         if (toTake != null) {
             pieces.remove(toTake);
         }

         // Test if attempted move puts king in check
         for (Piece piece : pieces) {
             if (piece.side != side) {
                 if (piece.isCheck(pieces, toTake)) {
                     if (toTake != null) {
                         pieces.add(toTake);
                     }
                     x = originalX;
                     y = originalY;
                     return true;
                 }
             }
         }

         if (toTake != null) {
            pieces.add(toTake);
         }


         x = originalX;
         y = originalY;
         return false;
    }

    public boolean take(ArrayList<Piece> pieces, Piece toTake, ArrayList<Piece> lostPieces) {
        if (toTake != null) {
            lostPieces.add(toTake);
            pieces.remove(toTake);
            return true;
        }
        return false;
    }

    public Piece getKing(ArrayList<Piece> pieces, String side) {
        for (Piece piece : pieces) {
            if (side == "light") {
                if (piece.side == "dark" && piece.type == "K") {
                    return piece;
                }
            }
            else {
                if (piece.side == "light" && piece.type == "K") {
                    return piece;
                }
            }
        }
        return null;
    }

    public boolean isPieceOnSquare(int pieceX, int pieceY, ArrayList<Piece> pieces, Piece toTake) {
        for (Piece piece : pieces) {
            if ((piece.x == pieceX && piece.y == pieceY)) {
                return true;
            }
        }
        return false;
    }

    public boolean blockedHorizontal(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake) {
        int i;
        int tempX = x;
        int tempY = y;
        int takeMod = 0;

        if (toTake != null) {
            takeMod = 1;
        }

        if (targetX == x) {
            if (targetY > y) {
                tempY++;
               for (i = 0; i < (targetY - y) - takeMod; i++){
                   if (isPieceOnSquare(tempX, tempY, pieces, toTake)){
                       return true;
                   }
                   tempY++;
               }
            }
            if (targetY < y) {
                tempY--;
                for (i = 0; i < (y - targetY) - takeMod; i++){
                    if (isPieceOnSquare(tempX, tempY, pieces, toTake)){
                        return true;
                    }
                    tempY--;
                }
            }
        }
        else if (targetY == y) {
            if (targetX > x) {
                tempX++;
                for (i = 0; i < (targetX - x) - takeMod; i++){
                    if (isPieceOnSquare(tempX, tempY, pieces, toTake)){
                        return true;
                    }
                    tempX++;
                }
            }
            if (targetX < x) {
                tempX--;
                for (i = 0; i < (x - targetX) - takeMod; i++){
                    if (isPieceOnSquare(tempX, tempY, pieces, toTake)){
                        return true;
                    }
                    tempX--;
                }
            }
        }
        return false;
    }

    public boolean blockedDiagonal(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake) {
        int i;
        int tempX = x;
        int tempY = y;
        int takeMod = 0;

        if (toTake != null) {
            takeMod = 1;
        }

        if (targetX > x && targetY > y) {
            tempY++;
            tempX++;
            for (i = 0; i < (targetY - y) - takeMod; i++) {
                if (isPieceOnSquare(tempX, tempY, pieces, toTake)) {
                    return true;
                }
                tempY++;
                tempX++;
            }
        }
        else if (targetX < x && targetY < y) {
            tempY--;
            tempX--;
            for (i = 0; i < (y - targetY) - takeMod; i++) {
                if (isPieceOnSquare(tempX, tempY, pieces, toTake)) {
                    return true;
                }
                tempY--;
                tempX--;
            }
        }
        else if (targetX < x && targetY > y) {
            tempX--;
            tempY++;
            for (i = 0; i < (targetY - y) - takeMod; i++) {
                if (isPieceOnSquare(tempX, tempY, pieces, toTake)) {
                    return true;
                }
                tempX--;
                tempY++;
            }
        }
        else if (targetX > x && targetY < y) {
            tempX++;
            tempY--;
            for (i = 0; i < (y - targetY) - takeMod; i++) {
                if (isPieceOnSquare(tempX, tempY, pieces, toTake)) {
                    return true;
                }
                tempX++;
                tempY--;
            }
        }
        return false;
    }

    public String getData() {
        // ex. LR00 or DP31
        // replaced "light" with L and "dark" with "D" to make them the same length, easier to work with
        return type + ( side == "light" ? "L" : "D" ) + x + y;
    }
}

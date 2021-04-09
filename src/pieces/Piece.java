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
import java.util.Map;

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

    public abstract Map<Integer, Integer> possibleMoves(ArrayList<Piece> pieces);
    // function to return opposite type of current piece
    public String oppositeSide() {
    	if (this.side == "dark") {
    		return "light";
    	}
    	else {
    		return "dark";
    	}
    }

    public Piece isOpponentOnSpace (ArrayList<Piece> pieces, int testX, int testY) {
        for (Piece piece: pieces) {
            if (piece.x == testX && piece.y == testY && piece.side != side) {
                return piece;
            }
        }
        return null;
    }

    public boolean isFriendlyOnSpace (ArrayList<Piece> pieces, int testX, int testY) {
        for (Piece piece: pieces) {
            if (piece.x == testX && piece.y == testY && piece.side == side) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isCheckmate(ArrayList<Piece> pieces) {

        ArrayList<Piece> tempPieces;
        Piece enemyKing;

        for (int i = 0; i <= 7; i++) {
            tempPieces = pieces;
            enemyKing = getKing(tempPieces, type);
            switch (i) {
                case 0:
                    enemyKing.x++;
                    enemyKing.y++;
                    break;
                case 1:
                    enemyKing.x--;
                    enemyKing.y--;
                    break;
                case 2:
                    enemyKing.x++;
                    enemyKing.y--;
                    break;
                case 3:
                    enemyKing.x--;
                    enemyKing.y++;
                    break;
                case 4:
                    enemyKing.x--;
                    break;
                case 5:
                    enemyKing.x++;
                    break;
                case 6:
                    enemyKing.y--;
                    break;
                case 7:
                    enemyKing.y++;
                    break;
            }
            tempPieces = moveKing(oppositeSide(), tempPieces, enemyKing.x, enemyKing.y);
            if (isInBounds(enemyKing) && !anyCheck(side, tempPieces)) {
                return false;
            }
        }
        return true;

    }

    public ArrayList<Piece> moveKing(String side, ArrayList<Piece> tempPieces, int x, int y) {
        for (Piece piece : tempPieces) {
            if (piece.side == side && piece.type == "K") {
                piece.x = x;
                piece.y = y;
            }
        }
        return tempPieces;
    }

    public boolean isInBounds(Piece piece) {
        if (piece.x <= 7 && piece.x >= 0 && piece.y <= 7 && piece.y >= 0) {
            return true;
        }
        return false;
    }

    public boolean anyCheck (String side, ArrayList<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece.side == side && piece.isCheck(pieces, null)) {
                return true;
            }
        }
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

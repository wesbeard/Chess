package pieces;

import processing.core.*;
import processing.sound.*;
import java.util.ArrayList;

public abstract class Piece extends PApplet{

    public String side;
    public String type;
    public int x;
    public int y;
    public String shapeFile;
    public PShape shape;
    public String pieceSet = "tatiana";
    public boolean moved = false;

    public abstract boolean move(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake, SoundFile castleSound, SoundFile takeSound, SoundFile moveSound);

    public abstract boolean isCheck(ArrayList<Piece> pieces, Piece toTake);

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

    public boolean take(ArrayList<Piece> pieces, Piece toTake) {
        if (toTake != null) {
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

    public char convertRank(int x) {
        switch (x) {
            case 0:
                return'a';
            case 1:
                return'b';
            case 2:
                return'c';
            case 3:
                return'd';
            case 4:
                return'e';
            case 5:
                return'f';
            case 6:
                return'g';
            case 7:
                return'h';
        }
        return ' ';
    }

    public int convertFile(int y) {
        return y++;
    }

    public String getData() {
        // ex. LR00 or DP31
        // replaced "light" with L and "dark" with "D" to make them the same length, easier to work with
        return type + ( side == "light" ? "L" : "D" ) + x + y;
    }
}

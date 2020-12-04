package pieces;

import processing.core.*;

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

    public abstract boolean move(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake);

    public boolean take(ArrayList<Piece> pieces, Piece toTake) {
        if (toTake != null) {
            pieces.remove(toTake);
            return true;
        }
        return false;
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
                       System.out.println("Path blocked");
                       return true;
                   }
                   tempY++;
               }
            }
            if (targetY < y) {
                tempY--;
                for (i = 0; i < (y - targetY) - takeMod; i++){
                    if (isPieceOnSquare(tempX, tempY, pieces, toTake)){
                        System.out.println("Path blocked");
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
                        System.out.println("Path blocked");
                        return true;
                    }
                    tempX++;
                }
            }
            if (targetX < x) {
                tempX--;
                for (i = 0; i < (x - targetX) - takeMod; i++){
                    if (isPieceOnSquare(tempX, tempY, pieces, toTake)){
                        System.out.println("Path blocked");
                        return true;
                    }
                    tempX--;
                }
            }
        }
        return false;
    }

    public boolean blockedDiagonal(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake) {
        return false;
    }
}

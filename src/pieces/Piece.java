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

    public void take(ArrayList<Piece> pieces, Piece toTake) {
        if (toTake != null) {
            pieces.remove(toTake);
        }
    }

    public boolean isPieceOnSquare(int pieceX, int pieceY, ArrayList<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece.x == pieceX && piece.y == pieceY) {
                return true;
            }
        }
        return false;
    }

    public boolean blockedHorizontal(int targetX, int targetY, ArrayList<Piece> pieces) {
        return true;
    }

    public boolean blockedDiagonal(int targetX, int targetY, ArrayList<Piece> pieces) {

        int directionX;
        int directionY;

        if (targetX > x) {
            directionX = 1;
        }
        else {
            directionX = -1;
        }
        if (targetY > y) {
            directionY = 1;
        }
        else {
            directionY = -1;
        }

        for (int i = 1; i < abs(targetX - x) - 1; i++) {
            if (isPieceOnSquare(x + i * directionX,y + i * directionY, pieces)) {
                System.out.println("Path blocked");
                return false;
            }
        }
        return true;
    }
}

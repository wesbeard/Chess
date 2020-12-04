package pieces;

import java.util.ArrayList;

public class Pawn extends Piece  {

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

    @Override
    public boolean move(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake){
        if (targetX == x) {
            if (side == "light") {
                if(targetY == y - 1 || (targetY == y - 2 && !moved)) {
                    super.x = targetX;
                    super.y = targetY;
                    moved = true;
                    return true;
                }
            }
            else {
                if(targetY == y + 1 || (targetY == y + 2 && !moved)) {
                    super.x = targetX;
                    super.y = targetY;
                    moved = true;
                    return true;
                }
            }
        }
        else if (targetX == x + 1 || targetX == x - 1) {
            if (side == "light") {
                if (targetY == y - 1) {
                    pieces.remove(toTake);
                    super.x = targetX;
                    super.y = targetY;
                    moved = true;
                    return true;
                }
            }
            else {
                if (targetY == y + 1) {
                    pieces.remove(toTake);
                    super.x = targetX;
                    super.y = targetY;
                    moved = true;
                    return true;
                }
            }
        }
        return false;
    }
}

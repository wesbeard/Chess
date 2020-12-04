package pieces;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop (String sideColor, int x, int y) {
        super.side = sideColor;
        super.type = "B";
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
    public boolean move(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake) {

        if ((targetX - x == targetY - y) || (-targetX + x == targetY - y) && !blockedDiagonal(targetX, targetY, pieces, toTake)) {
            take(pieces, toTake);
            super.x = targetX;
            super.y = targetY;
            moved = true;
            return true;
        }
        return false;
    }
}

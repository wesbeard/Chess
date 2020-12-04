package pieces;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight (String sideColor, int x, int y) {
        super.side = sideColor;
        super.type = "N";
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
        int absX = abs(x - targetX);
        int absY = abs(y - targetY);

        if((absX == 2 && absY == 1) || (absX == 1 && absY == 2)) {
            take(pieces, toTake);
            super.x = targetX;
            super.y = targetY;
            moved = true;
            return true;
        }
        return false;
    }
}

package pieces;

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
}

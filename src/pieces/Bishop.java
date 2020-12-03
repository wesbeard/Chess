package pieces;

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
}

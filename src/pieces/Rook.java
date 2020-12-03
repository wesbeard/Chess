package pieces;

public class Rook extends Piece {

    public Rook (String sideColor, int x, int y) {
        super.side = sideColor;
        super.type = "R";
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

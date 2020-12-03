package pieces;

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

}

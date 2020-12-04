package pieces;

public class PieceFactory {

    public static Piece getPiece(String pieceType, String sideColor, int startX, int startY) {
        if (pieceType == null) {
            return null;
        }
        else if (pieceType == "P") {
            return new Pawn(sideColor, startX, startY);
        }
        else if (pieceType == "K") {
            return new King(sideColor, startX, startY);
        }
        else if (pieceType == "Q") {
            return new Queen(sideColor, startX, startY);
        }
        else if (pieceType == "N") {
            return new Knight(sideColor, startX, startY);
        }
        else if (pieceType == "B") {
            return new Bishop(sideColor, startX, startY);
        }
        else if (pieceType == "R") {
            return new Rook(sideColor, startX, startY);
        }
        return null;
    }
}

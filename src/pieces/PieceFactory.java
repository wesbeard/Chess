package pieces;

public class PieceFactory {

    public static Piece getPiece(String pieceType, String sideColor, int startX, int startY) {
        if (pieceType == null) {
            return null;
        }
        else if (pieceType.equals("P")) {
            return new Pawn(sideColor, startX, startY);
        }
        else if (pieceType.equals("K")) {
            return new King(sideColor, startX, startY);
        }
        else if (pieceType.equals("Q")) {
            return new Queen(sideColor, startX, startY);
        }
        else if (pieceType.equals("N")) {
            return new Knight(sideColor, startX, startY);
        }
        else if (pieceType.equals("B")) {
            return new Bishop(sideColor, startX, startY);
        }
        else if (pieceType.equals("R")) {
            return new Rook(sideColor, startX, startY);
        }
        return null;
    }
}

/*
 * Wes Beard - wesley.beard@mymail.champlain.edu
 * Michael Leonard - michael.leonard@mymail.chamamplain.edu
 * CSI-340 Final Project
 * 12/7/2020
 *
 * Written by Wes Beard
 *
 * This file contains the piece factory code which constructs
 * pieces based on given data which allows it to create every
 * different type of piece in one class
 */

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

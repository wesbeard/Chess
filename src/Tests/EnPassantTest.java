package Tests;

import org.junit.jupiter.api.Test;
import pieces.Piece;
import pieces.PieceFactory;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EnPassantTest {

    @Test
    public void testEnPassant() {
        ArrayList<Piece> lostPieces = new ArrayList<>();
        ArrayList<Piece> testBoard = new ArrayList<>();

        Piece testPassant = PieceFactory.getPiece("P","dark", 0, 2);
        testPassant.canEnPassant = true;
        testBoard.add(testPassant);
        Piece testPawn = PieceFactory.getPiece("P","light", 1, 2);
        testBoard.add(testPawn);

        assertTrue(testPawn.enPassant(0, 1, testBoard, null, lostPieces));
    }

    @Test
    public void testEnPassantWithTake() {
        ArrayList<Piece> lostPieces = new ArrayList<>();
        ArrayList<Piece> testBoard = new ArrayList<>();

        Piece testPassant = PieceFactory.getPiece("P","dark", 0, 2);
        testPassant.canEnPassant = true;
        testBoard.add(testPassant);
        Piece testPawn = PieceFactory.getPiece("P","light", 1, 2);
        testBoard.add(testPawn);
        Piece testTake = PieceFactory.getPiece("P","dark", 0, 1);
        testBoard.add(testTake);

        assertFalse(testPawn.enPassant(0, 1, testBoard, testTake, lostPieces));
    }

    @Test
    public void testEnPassantWithPin() {
        ArrayList<Piece> lostPieces = new ArrayList<>();
        ArrayList<Piece> testBoard = new ArrayList<>();

        Piece testPassant = PieceFactory.getPiece("P","dark", 0, 2);
        testPassant.canEnPassant = true;
        testBoard.add(testPassant);
        Piece testPawn = PieceFactory.getPiece("P","light", 1, 2);
        testBoard.add(testPawn);
        Piece testKing = PieceFactory.getPiece("K","light", 1, 5);
        testBoard.add(testKing);
        Piece testRook = PieceFactory.getPiece("R","dark", 1, 1);
        testBoard.add(testRook);

        assertFalse(testPawn.enPassant(0, 1, testBoard, null, lostPieces));
    }
}

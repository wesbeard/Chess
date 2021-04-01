package Tests;

import org.junit.jupiter.api.Test;
import pieces.Piece;
import pieces.PieceFactory;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PinTest {

    @Test
    public void testPinned() {
        ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testPawn = PieceFactory.getPiece("P","light", 0, 2);
        testBoard.add(testPawn);
        Piece testRook = PieceFactory.getPiece("R","dark", 0, 3);
        testBoard.add(testRook);
        Piece testKing = PieceFactory.getPiece("K", "light", 0, 0);
        testBoard.add(testKing);
        // Validate if pawn is pinned by rook
        assertTrue(testPawn.isPinned(testBoard, 1, 2, null));
    }

    @Test
    public void testPinnedWithTake() {
        ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testRook = PieceFactory.getPiece("R","light", 5, 5);
        testBoard.add(testRook);
        Piece testPawn = PieceFactory.getPiece("P","dark", 5, 4);
        testBoard.add(testPawn);
        Piece testBishop = PieceFactory.getPiece("B","dark", 6, 6);
        testBoard.add(testBishop);
        Piece testKing = PieceFactory.getPiece("K", "light", 4, 4);
        testBoard.add(testKing);
        // Validate if rook is pinned by bishop with an attempted take on dark pawn
        assertTrue(testRook.isPinned(testBoard, 5, 4, testPawn));
    }
}

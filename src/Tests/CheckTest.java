package Tests;

import org.junit.jupiter.api.Test;
import pieces.Piece;
import pieces.PieceFactory;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckTest {

    @Test
    public void testCheckRook() {
        ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testRook = PieceFactory.getPiece("R","light", 0, 0);
        testBoard.add(testRook);
        Piece testKing = PieceFactory.getPiece("K", "dark", 0, 1);
        testBoard.add(testKing);
        // Validate check with rook
        assertTrue(testRook.isCheck(testBoard, testKing));
    }

    @Test
    public void testCheckBishop() {
        ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testBishop = PieceFactory.getPiece("B","light", 0, 0);
        testBoard.add(testBishop);
        Piece testKing = PieceFactory.getPiece("K", "dark", 7, 7);
        testBoard.add(testKing);
        // Validate check with bishop
        assertTrue(testBishop.isCheck(testBoard, testKing));
    }

    @Test
    public void testCheckPawn() {
        ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testPawn = PieceFactory.getPiece("P","light", 1, 1);
        testBoard.add(testPawn);
        Piece testKing = PieceFactory.getPiece("K", "dark", 0, 0);
        testBoard.add(testKing);
        // Validate check with pawn
        assertTrue(testPawn.isCheck(testBoard, testKing));
    }

    @Test
    public void testCheckKnight() {
        ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testKnight = PieceFactory.getPiece("N","light", 2, 1);
        testBoard.add(testKnight);
        Piece testKing = PieceFactory.getPiece("K", "dark", 0, 0);
        testBoard.add(testKing);
        // Validate check with knight
        assertTrue(testKnight.isCheck(testBoard, testKing));
    }

    @Test
    public void testCheckQueen() {
        ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testQueen = PieceFactory.getPiece("Q","light", 0, 7);
        testBoard.add(testQueen);
        Piece testKing = PieceFactory.getPiece("K", "dark", 0, 0);
        testBoard.add(testKing);
        // Validate check with queen
        assertTrue(testQueen.isCheck(testBoard, testKing));
    }
}

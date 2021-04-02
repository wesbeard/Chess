package Tests;

import org.junit.jupiter.api.Test;
import pieces.Piece;
import pieces.PieceFactory;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MateTest {

    @Test
    public void testCheckmate() {
        ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testRook = PieceFactory.getPiece("R","dark", 3, 2);
        testBoard.add(testRook);
        Piece testQueen = PieceFactory.getPiece("Q","dark", 0, 1);
        testBoard.add(testQueen);
        Piece testKing = PieceFactory.getPiece("K", "light", 3, 0);
        testBoard.add(testKing);
        // Tests a basic checkmate with queen and rook
        // (I'm thinking that it'll work similar to pinned where it
        //  needs the space the piece is moving to)
        // This example moves the queen from 0, 1 to 3, 1 which should be checkmate
        assertTrue(testQueen.isCheckmate(testBoard, 3, 1) == true);
    }
    
    @Test
    public void testFalseCheckmate() {
        ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testRook = PieceFactory.getPiece("R","dark", 3, 2);
        testBoard.add(testRook);
        Piece testQueen = PieceFactory.getPiece("Q","dark", 0, 1);
        testBoard.add(testQueen);
        Piece testKing = PieceFactory.getPiece("K", "light", 3, 0);
        testBoard.add(testKing);
        // This example moves the queen from 0, 1 to 3, 1 which should be checkmate
        assertTrue(testQueen.isCheckmate(testBoard, 0, 0) == true);
    }
    @Test
    public void testFalseCheckmate2() {
        ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testKing = PieceFactory.getPiece("K","light", 3, 0);
        testBoard.add(testRook);
        Piece testPawn = PieceFactory.getPiece("P","light", 3, 1);
        testBoard.add(testQueen);
        Piece testBishop = PieceFactory.getPiece("B", "light", 4, 0);
        testBoard.add(testKing);
        Piece testKnight = PieceFactory.getPiece("N", "light", 2, 0);
        testBoard.add(testKnight);
        Piece testQueen = PieceFactory.getPiece("Q", "dark", 0, 0);
        testBoard.add(testQueen);
        // This example moves the queen from 0, 1 to 3, 1 which should be check but not mate
        assertTrue(testQueen.isCheckmate(testBoard, 0, 3) == true);
    }
}

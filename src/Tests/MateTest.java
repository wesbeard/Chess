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
        /* WORK IN PROGRESS */
        assertTrue(testQueen.isCheckmate(testBoard, 3, 1));
    }
}

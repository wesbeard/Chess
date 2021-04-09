package Tests;

import main.Chess;
import org.junit.jupiter.api.Test;
import pieces.Piece;
import pieces.PieceFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PossibleMovesTest {

    @Test
    public void testPossibleMovesPawn() {
        ArrayList<Piece> testBoard = new ArrayList<>();
        Map<Integer, Integer> expected = new HashMap<>();
        Map<Integer, Integer> actual = new HashMap<>();

        Piece testPawn = PieceFactory.getPiece("P","light", 4, 4);
        testBoard.add(testPawn);
        testPawn.moved = false;

        expected.put(4, 3);

        actual = testPawn.possibleMoves(testBoard);

        assertEquals(expected, actual);
    }

    @Test
    public void testPossibleMovesPawnTake() {
        ArrayList<Piece> testBoard = new ArrayList<>();
        Map<Integer, Integer> expected = new HashMap<>();
        Map<Integer, Integer> actual = new HashMap<>();

        Piece testPawn = PieceFactory.getPiece("P","light", 3, 3);
        testBoard.add(testPawn);
        testPawn.moved = true;
        Piece testTake = PieceFactory.getPiece("P","dark", 2, 2);
        testBoard.add(testTake);

        expected.put(3, 2);
        expected.put(2, 2);

        actual = testPawn.possibleMoves(testBoard);
        assertEquals(expected, actual);
    }
}

package Tests;

import pieces.Piece;
import pieces.PieceFactory;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestValidMove {

	@Test
	public void TestValidMove1() {
		// create test scenario
		ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testRook = PieceFactory.getPiece("R","dark", 3, 2);
        testBoard.add(testRook);
        Piece testQueen = PieceFactory.getPiece("Q","dark", 0, 0);
        testBoard.add(testQueen);
        Piece testKing = PieceFactory.getPiece("K", "light", 3, 0);
        testBoard.add(testKing);
        
        assertEquals(testKing.isValidMove(testBoard, 4, 1, null), true);
	}
	
	@Test
	public void TestInvalidMove1() {
		// create test scenario
		ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testKing = PieceFactory.getPiece("K","light", 3, 0);
        testBoard.add(testKing);
        Piece testPawn = PieceFactory.getPiece("P","light", 3, 1);
        testBoard.add(testPawn);
        Piece testBishop = PieceFactory.getPiece("B", "light", 4, 0);
        testBoard.add(testBishop);
        Piece testKnight = PieceFactory.getPiece("N", "light", 2, 0);
        testBoard.add(testKnight);
        Piece testQueen = PieceFactory.getPiece("Q", "dark", 0, 0);
        testBoard.add(testQueen);
        
        assertFalse(testKnight.isValidMove(testBoard, 1, 2, null));
	}
	
	@Test
	public void TestValidMove2() {
		// create test scenario
		ArrayList<Piece> testBoard = new ArrayList<>();
		Piece testKing = PieceFactory.getPiece("K","light", 3, 0);
        testBoard.add(testKing);
        Piece testPawn = PieceFactory.getPiece("N","light", 3, 1);
        testBoard.add(testPawn);
        Piece testBishop = PieceFactory.getPiece("B", "light", 4, 0);
        testBoard.add(testBishop);
        Piece testKnight = PieceFactory.getPiece("R", "light", 2, 0);
        testBoard.add(testKnight);
        Piece testRook = PieceFactory.getPiece("N","dark", 4, 2);
        testBoard.add(testRook);
        
        assertEquals(testKing.isValidMove(testBoard, 4, 1, null), true);
	}
}

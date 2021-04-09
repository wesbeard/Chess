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
        
        // make demo lost pieces
        ArrayList<Piece> lostPieces = new ArrayList<>();
        
        assertTrue(testKing.isValidMove(testBoard, 4, 1, null, lostPieces));
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
        
        // make demo lost pieces
        ArrayList<Piece> lostPieces = new ArrayList<>();
        
        assertFalse(testKnight.isValidMove(testBoard, 1, 2, null, lostPieces));
	}
	
	@Test
	public void TestValidMove2() {
		// create test scenario
		ArrayList<Piece> testBoard = new ArrayList<>();
		Piece testKing = PieceFactory.getPiece("K","light", 3, 0);
        testBoard.add(testKing);
        Piece testKnight = PieceFactory.getPiece("N","light", 3, 1);
        testBoard.add(testKnight);
        Piece testBishop = PieceFactory.getPiece("B", "light", 4, 0);
        testBoard.add(testBishop);
        Piece testRook = PieceFactory.getPiece("R", "light", 2, 0);
        testBoard.add(testRook);
        Piece testKnight2 = PieceFactory.getPiece("N","dark", 4, 2);
        testBoard.add(testKnight2);
        
        // make demo lost pieces
        ArrayList<Piece> lostPieces = new ArrayList<>();
        
        assertEquals(testKing.isValidMove(testBoard, 4, 1, null, lostPieces), true);
	}
	
	@Test
	public void TestInvalidMove2() {
		// create test scenario
		ArrayList<Piece> testBoard = new ArrayList<>();
		Piece testLKing = PieceFactory.getPiece("K","light", 0, 0);
        testBoard.add(testLKing);
        Piece testDKing = PieceFactory.getPiece("K","dark", 7, 7);
        testBoard.add(testDKing);
        Piece testPawn = PieceFactory.getPiece("P", "light", 0, 1);
        testBoard.add(testPawn);
        
        // make demo lost pieces
        ArrayList<Piece> lostPieces = new ArrayList<>();
        
        assertNotEquals(testPawn.isValidMove(testBoard, 0, 4, null, lostPieces), true);
	}
	
	@Test
	public void TestInvalidMove3() {
		// create test scenario
		ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testQueen = PieceFactory.getPiece("Q","dark", 0, 0);
        testBoard.add(testQueen);
        Piece testKing = PieceFactory.getPiece("K", "light", 3, 0);
        testBoard.add(testKing);
        Piece testPawn = PieceFactory.getPiece("P", "light", 2, 6);
        testBoard.add(testPawn);
        
        // make demo lost pieces
        ArrayList<Piece> lostPieces = new ArrayList<>();
        
        assertFalse(testPawn.isValidMove(testBoard, 2, 7, null, lostPieces));
	}
}

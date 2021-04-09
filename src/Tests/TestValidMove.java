package Tests;

import pieces.Piece;
import pieces.PieceFactory;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestValidMove {

	@Test
	public void TestValidMove1() {
		// TODO
		ArrayList<Piece> testBoard = new ArrayList<>();
        Piece testRook = PieceFactory.getPiece("R","dark", 3, 2);
        testBoard.add(testRook);
        Piece testQueen = PieceFactory.getPiece("Q","dark", 0, 0);
        testBoard.add(testQueen);
        Piece testKing = PieceFactory.getPiece("K", "light", 3, 0);
        testBoard.add(testKing);
        
        assertEquals(testKing.isValidMove(testBoard, 4, 1, null), true);
	}
	
}

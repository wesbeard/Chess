import pieces.*;
import processing.core.*;
import java.awt.*;
import java.util.ArrayList;

public class Main extends PApplet {

    final int boardSize = 800;
    final int numSpaces = 8;
    final int squareSize = boardSize/numSpaces;
    int fillColor = 0;

    ArrayList<Piece> pieces = new ArrayList<Piece>();

    Color light = new Color(255, 255, 221);
    Color dark = new Color(104, 136, 72);

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        size (boardSize, boardSize);
    }

    public void setup() {
        background(0, 0, 0);
        setPiecePositions();

    }

    public void draw() {
        drawBoard();
        drawPieces();
    }

    public void setPiecePositions() {
         /*
         Create Dark Pieces
         */

        // Pawns
        Piece dPawn1 = new Pawn("dark", 0 , 1);
        pieces.add(dPawn1);
        Piece dPawn2 = new Pawn("dark", 1 , 1);
        pieces.add(dPawn2);
        Piece dPawn3 = new Pawn("dark", 2 , 1);
        pieces.add(dPawn3);
        Piece dPawn4 = new Pawn("dark", 3 , 1);
        pieces.add(dPawn4);
        Piece dPawn5 = new Pawn("dark", 4 , 1);
        pieces.add(dPawn5);
        Piece dPawn6 = new Pawn("dark", 5 , 1);
        pieces.add(dPawn6);
        Piece dPawn7 = new Pawn("dark", 6 , 1);
        pieces.add(dPawn7);
        Piece dPawn8 = new Pawn("dark", 7 , 1);
        pieces.add(dPawn8);

        // Rooks
        Piece dRook1 = new Rook("dark",0, 0);
        pieces.add(dRook1);
        Piece dRook2 = new Rook("dark",7, 0);
        pieces.add(dRook2);

        // Knights
        Piece dKnight1 = new Knight("dark", 1, 0);
        pieces.add(dKnight1);
        Piece dKnight2 = new Knight("dark", 6, 0);
        pieces.add(dKnight2);

        // Bishops
        Piece dBishop1 = new Bishop("dark", 2, 0);
        pieces.add(dBishop1);
        Piece dBishop2 = new Bishop("dark", 5, 0);
        pieces.add(dBishop2);

        // Queen
        Piece dQueen = new Queen("dark", 3, 0);
        pieces.add(dQueen);

        // King
        Piece dKing = new King("dark", 4, 0);
        pieces.add(dKing);

        /*
        Create Light Pieces
         */

        // Pawns
        Piece lPawn1 = new Pawn("light", 0 , 6);
        pieces.add(lPawn1);
        Piece lPawn2 = new Pawn("light", 1 , 6);
        pieces.add(lPawn2);
        Piece lPawn3 = new Pawn("light", 2 , 6);
        pieces.add(lPawn3);
        Piece lPawn4 = new Pawn("light", 3 , 6);
        pieces.add(lPawn4);
        Piece lPawn5 = new Pawn("light", 4 , 6);
        pieces.add(lPawn5);
        Piece lPawn6 = new Pawn("light", 5 , 6);
        pieces.add(lPawn6);
        Piece lPawn7 = new Pawn("light", 6 , 6);
        pieces.add(lPawn7);
        Piece lPawn8 = new Pawn("light", 7 , 6);
        pieces.add(lPawn8);

        // Rooks
        Piece lRook1 = new Rook("light",0, 7);
        pieces.add(lRook1);
        Piece lRook2 = new Rook("light",7, 7);
        pieces.add(lRook2);

        // Knights
        Piece lKnight1 = new Knight("light", 1, 7);
        pieces.add(lKnight1);
        Piece lKnight2 = new Knight("light", 6, 7);
        pieces.add(lKnight2);

        // Bishops
        Piece lBishop1 = new Bishop("light", 2, 7);
        pieces.add(lBishop1);
        Piece lBishop2 = new Bishop("light", 5, 7);
        pieces.add(lBishop2);

        // Queen
        Piece lQueen = new Queen("light", 3, 7);
        pieces.add(lQueen);

        // King
        Piece lKing = new King("light", 4, 7);
        pieces.add(lKing);
    }

    public void drawPieces() {
        for (Piece piece : pieces) {
            piece.shape = loadShape(piece.shapeFile);
            shape(piece.shape, piece.x * squareSize, piece.y * squareSize, squareSize, squareSize);
        }
    }

    public void drawBoard() {

        for(int i = 0; i < numSpaces; i++){
            for(int j = 0; j < numSpaces;j++){

                fill(fillColor);
                noStroke();
                rect(j * squareSize, i * squareSize, squareSize, squareSize);

                if(fillColor == dark.getRGB()){
                    fillColor = light.getRGB();
                }
                else{
                    fillColor = dark.getRGB();
                }
            }
            if(numSpaces %2 == 0 && fillColor == dark.getRGB()){
                fillColor = light.getRGB();
            }
            else if(numSpaces %2 == 0 && fillColor == light.getRGB()){
                fillColor = dark.getRGB();
            }

        }
    }
}
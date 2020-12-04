import pieces.*;
import processing.core.*;
import java.awt.*;
import java.util.ArrayList;

public class Main extends PApplet {

    final int boardSize = 1000;
    final int numSpaces = 8;
    final int squareSize = boardSize/numSpaces;
    int fillColor = 0;

    ArrayList<Piece> pieces = new ArrayList<Piece>();

    Piece selected;
    Piece toTake;
    boolean lightsTurn = true;
    boolean pieceClicked;
    int targetX;
    int targetY;

    Color light = new Color(255, 255, 221);
    Color dark = new Color(104, 136, 72);
    Color highlight = new Color(200, 100, 100);

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        size (boardSize, boardSize);
    }

    public void setup() {
        background(0, 0, 0);
        Setup.setPiecePositions(pieces);
    }

    public void draw() {
        drawBoard();
        drawPieces();
        noFill();
        stroke(highlight.getRGB(),200);
        strokeWeight(5);
        if (selected != null) {
            /* \/Optional circle highlighting\/ */
            // circle((selected.x * squareSize) + (squareSize / 2), (selected.y * squareSize) + (squareSize / 2), squareSize - 10);
            rect((selected.x * squareSize), (selected.y * squareSize), squareSize, squareSize);
        }
    }

    public void mousePressed() {
        pieceClicked = false;
        toTake = null;
        targetX = mouseX / squareSize;
        targetY = mouseY / squareSize;

        // Check if clicked on a piece
        for (Piece piece : pieces) {
            int pieceX = (piece.x * squareSize) + squareSize;
            int pieceY = (piece.y * squareSize) + squareSize;

            if ((mouseX > pieceX - squareSize && mouseX < pieceX) && (mouseY > pieceY - squareSize && mouseY < pieceY)) {
                if (selected == null && lightsTurn && piece.side == "light") {
                    selected = piece;
                    pieceClicked = true;
                    break;
                }
                else if (lightsTurn && piece.side == "light" || !lightsTurn && piece.side == "dark"){
                    selected = piece;
                    pieceClicked = true;
                    break;
                }
                else if (lightsTurn && piece.side == "dark" || !lightsTurn && piece.side == "light"){
                    pieceClicked = true;
                    toTake = piece;
                    break;
                }
            }
        }

        //If piece is selected
        if (selected != null) {
            if (!pieceClicked || toTake != null) {
                if (selected.move(targetX, targetY, pieces, toTake)) {
                    selected = null;
                    if (lightsTurn) {
                        lightsTurn = false;
                    } else {
                        lightsTurn = true;
                    }
                }
            }
        }
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
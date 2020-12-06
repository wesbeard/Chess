import pieces.*;
import processing.core.*;
import processing.sound.*;
import java.awt.*;
import java.util.ArrayList;


public class Chess extends PApplet {

    // Sounds
    SoundFile moveSound;
    SoundFile takeSound;
    SoundFile castleSound;

    // Board settings
    final int boardSize = 1000;
    final int numSpaces = 8;
    final int squareSize = boardSize/numSpaces;

    // Piece/location variables
    ArrayList<Piece> pieces = new ArrayList<Piece>();
    Piece selected;
    Piece toTake;
    boolean lightsTurn = true;
    boolean pieceClicked;
    int targetX;
    int targetY;

    // Appearance/color variables
    PImage icon;
    int fillColor = 0;
    Color light = new Color(255, 255, 221);
    Color dark = new Color(104, 136, 72);
    Color highlight = new Color(200, 100, 100);

    BoardReplay replay = new BoardReplay();

    public static void main(String[] args) {
        PApplet.main("Chess");
    }

    public void settings() {
        size(boardSize, boardSize);
    }

    public void setup() {
        // Sounds
        moveSound = new SoundFile(this, "sound/Move.mp3");
        moveSound.amp((float) .4);
        takeSound = new SoundFile(this, "sound/Take.mp3");
        takeSound.amp((float) .8);
        castleSound = new SoundFile(this, "sound/Castle.mp3");
        castleSound.amp((float) .4);

        // Window
        icon = loadImage("images/icon.png");
        surface.setIcon(icon);
        cursor(HAND);
        surface.setTitle("Chess!");
        background(0, 0, 0);

        // Game
        Setup.setPiecePositions(pieces);
        replay.performRecordCommand(pieces);
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

    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == LEFT) {
                pieces = replay.stepBack();
                // swap turns as you go back in time
                lightsTurn = !lightsTurn;
            } else if (keyCode == RIGHT) {
                pieces = replay.stepForward();
                // swap turns as you go back in time
                lightsTurn = !lightsTurn;
            }
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
                // Check if first move
                if (selected == null && lightsTurn && piece.side == "light") {
                    selected = piece;
                    pieceClicked = true;
                    break;
                }
                // Check if clicked on same side's piece
                else if (lightsTurn && piece.side == "light" || !lightsTurn && piece.side == "dark"){
                    selected = piece;
                    pieceClicked = true;
                    break;
                }
                // Check if clicked on opposing side's piece
                else if (lightsTurn && piece.side == "dark" || !lightsTurn && piece.side == "light"){
                    pieceClicked = true;
                    toTake = piece;
                    break;
                }
            }
        }

        //Check if piece is selected
        if (selected != null) {
            // Check if a piece is clicked or a piece is to be taken
            if (!pieceClicked || toTake != null) {
                // If move is successful switch sides
                if (selected.move(targetX, targetY, pieces, toTake, castleSound, takeSound, moveSound)) {
                    // pass in entire board to be recorded at this pos
                    replay.performRecordCommand(pieces);
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

    // Calls every frame to draw pieces
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
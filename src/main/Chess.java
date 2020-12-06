package main;

import interactivity.Popup;
import interactivity.iButton;
import pieces.*;
import processing.core.*;
import processing.sound.*;
import java.util.ArrayList;
import timing.GameTimer;

import static main.Constants.*;


public class Chess extends PApplet {

    // Board settings
    final int boardSize = 1000;
    final int numSpaces = 8;
    final int squareSize = boardSize/numSpaces;

    // Piece/location variables
    ArrayList<Piece> pieces = new ArrayList<Piece>();
    Piece selected;
    Piece toTake;
    boolean lightsTurn = true;
    boolean firstMoveLight = true;
    boolean firstMoveDark = true;
    boolean pieceClicked;
    int targetX;
    int targetY;
    boolean ableToSwitch;

    // Appearance/color variables
    PImage icon;
    int fillColor = 0;

    // Replay element
    BoardReplay replay;

    // UI elements
    int menuWidth = 300;
    ArrayList<iButton> buttons = new ArrayList<iButton>();
    boolean popup = false;

    // Timer variables
    GameTimer darkTimer = new GameTimer();
    GameTimer lightTimer = new GameTimer();
    String darkTimerValue = "10:00";
    String lightTimerValue = "10:00";




    public static void main(String[] args) {
        PApplet.main("main.Chess");
    }

    public void settings() {
        size(boardSize + menuWidth, boardSize);
    }

    public void setup() {
        // Sounds
        MOVESOUND = new SoundFile(this, "sound/Move.mp3");
        MOVESOUND.amp((float) .4);
        TAKESOUND = new SoundFile(this, "sound/Take.mp3");
        TAKESOUND.amp((float) .6);
        CASTLESOUND = new SoundFile(this, "sound/Castle.mp3");
        CASTLESOUND.amp((float) .4);
        VICTORYSOUND = new SoundFile(this, "sound/Victory.mp3");
        VICTORYSOUND.amp((float) .2);
        INVALIDSOUND = new SoundFile(this, "sound/Invalid.wav");
        INVALIDSOUND.amp((float) .4);
        CHECKSOUND = new SoundFile(this, "sound/Check.wav");
        CHECKSOUND.amp((float) .1);

        // Window
        icon = loadImage("images/icon.png");
        surface.setIcon(icon);
        cursor(HAND);
        surface.setTitle("Chess!");
        background(LIGHT.getRGB());

        // Game
        Setup.setPiecePositions(pieces);
        replay = new BoardReplay(pieces);

        // UI
        textSize(36);
        textAlign(CENTER, CENTER);
        Setup.addButtons(buttons);
    }

    public void draw() {

        if (!firstMoveLight && firstMoveDark && !lightTimer.started) {
            lightTimer.start();
            lightTimer.started = true;
        }
        if (firstMoveDark && !firstMoveLight && !darkTimer.started){
            darkTimer.start();
            darkTimer.started = true;
        }
        if (lightTimer.started) {
            lightTimerValue = lightTimer.getTime();
        }
        if (darkTimer.started) {
            darkTimerValue = darkTimer.getTime();
        }

        background(BACKGROUND.getRGB());
        drawTurnIndicator();
        drawTimers();
        drawBoard();
        drawPieces();
        drawSelection();
        if (popup) {
            drawPopup();
        }
        drawButtons();
    }

    public void keyPressed() {
        ableToSwitch = false;
        if (key == CODED) {
            if (keyCode == LEFT) {
                ableToSwitch =  replay.stepBack(pieces);
                // swap turns as you go back in time
                if (ableToSwitch) {
                    MOVESOUND.play();
                    lightsTurn = !lightsTurn;
                }
            } else if (keyCode == RIGHT) {
                ableToSwitch = replay.stepForward(pieces);
                // swap turns as you go back in time
                if (ableToSwitch) {
                    MOVESOUND.play();
                    lightsTurn = !lightsTurn;
                }
            }
        }
    }

    public void mousePressed() {

        pieceClicked = false;
        boolean buttonClicked = false;
        toTake = null;
        targetX = mouseX / squareSize;
        targetY = mouseY / squareSize;

        // Check if clicked on UI element
        for (iButton button : buttons) {
            if(button.clicked(mouseX, mouseY)){
                buttonClicked = true;
                if (button.id == "Light Resigns") {
                    resetBoard();
                    Popup.text = "Black Wins!";


                }
                else if (button.id == "Dark Resigns") {
                    resetBoard();
                    Popup.text = "White Wins!";
                }
                else if (button.id == "Draw") {
                    resetBoard();
                    Popup.text = "Draw!";
                }
                else if (button.id == "Ok") {
                    button.hidden = true;
                    popup = false;
                }
            }
        }

        if (!buttonClicked) {
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
                    else if (lightsTurn && piece.side == "light" || !lightsTurn && piece.side == "dark") {
                        selected = piece;
                        pieceClicked = true;
                        break;
                    }
                    // Check if clicked on opposing side's piece
                    else if (lightsTurn && piece.side == "dark" || !lightsTurn && piece.side == "light") {
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
                    if (replay.performRecordCommand(selected, targetX, targetY, pieces, toTake)) {
                        selected = null;
                        if (lightsTurn) {
                            if (!firstMoveLight) {
                                lightTimer.increment();
                            }
                            lightTimer.pause();
                            darkTimer.resume();
                            firstMoveLight = false;
                            lightsTurn = false;
                        } else {
                            if (!firstMoveDark) {
                                darkTimer.increment();
                            }
                            darkTimer.pause();
                            lightTimer.resume();
                            firstMoveDark = false;
                            lightsTurn = true;
                        }
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

    // Calls every frame to draw board
    public void drawBoard() {

        for(int i = 0; i < numSpaces; i++){
            for(int j = 0; j < numSpaces;j++){

                fill(fillColor);
                noStroke();
                rect(j * squareSize, i * squareSize, squareSize, squareSize);

                if(fillColor == DARK.getRGB()){
                    fillColor = LIGHT.getRGB();
                }
                else{
                    fillColor = DARK.getRGB();
                }
            }
            if(numSpaces %2 == 0 && fillColor == DARK.getRGB()){
                fillColor = LIGHT.getRGB();
            }
            else if(numSpaces %2 == 0 && fillColor == LIGHT.getRGB()){
                fillColor = DARK.getRGB();
            }

        }
    }

    public void drawSelection() {
        noFill();
        stroke(HIGHLIGHT.getRGB(), 200);
        strokeWeight(5);
        if (selected != null) {
            /* \/Optional circle highlighting\/ */
            // circle((selected.x * squareSize) + (squareSize / 2), (selected.y * squareSize) + (squareSize / 2), squareSize - 10);
            rect((selected.x * squareSize), (selected.y * squareSize), squareSize, squareSize);
        }
    }

    public void drawButtons() {
        rectMode(CENTER);
        stroke(BUTTONEDGE.getRGB());
        strokeWeight(2);
        for (iButton button : buttons) {
            if (!button.hidden) {
                fill(button.color.getRGB());
                rect(button.x, button.y, button.width, button.height, 10);
                fill(BLACK.getRGB());
                text(button.text, button.x, button.y - 5);
            }
        }
        rectMode(CORNER);
    }

    public void drawPopup() {
        rectMode(CENTER);
        stroke(BUTTONEDGE.getRGB());
        strokeWeight(2);
        fill(WHITE.getRGB());
        rect(Popup.x, Popup.y, Popup.width, Popup.height, 10);
        fill(BLACK.getRGB());
        text(Popup.text, Popup.x, Popup.y - 30);
        rectMode(CORNER);
    }

    public void showPopupButton() {
        for (iButton button: buttons) {
            if (button.id == "Ok") {
                button.hidden = false;
            }
        }
    }

    public void drawTurnIndicator() {
        stroke(BLACK.getRGB());
        strokeWeight(3);
        if (lightsTurn) {
            fill(LIGHT.getRGB());
            rect(boardSize + 10, boardSize / 2 + 30, 10, (boardSize / 2) - 50, 50);
        }
        else {
            fill(DARK.getRGB());
            rect(boardSize + 10, 30, 10, (boardSize / 2) - 50, 50);
        }
    }

    public void drawTimers() {
        rectMode(CENTER);
        stroke(BUTTONEDGE.getRGB());
        strokeWeight(2);
        fill(DARK.getRGB());
        rect(1150, 400, 150, 50, 10);
        fill(BLACK.getRGB());
        text(darkTimerValue,1150, 395);
        fill(LIGHT.getRGB());
        rect(1150, 600, 150, 50, 10);
        fill(BLACK.getRGB());
        text(lightTimerValue,1150, 595);
        rectMode(CORNER);
    }

    public void resetBoard() {
        popup = true;
        VICTORYSOUND.play();
        showPopupButton();
        lightTimer.pause();
        darkTimer.pause();
        lightTimer.reset();
        darkTimer.reset();
        pieces.clear();
        Setup.setPiecePositions(pieces);
        lightsTurn = true;
        firstMoveLight = true;
        firstMoveDark = true;
        replay = new BoardReplay(pieces);
        System.out.println("New game started!");
    }

}
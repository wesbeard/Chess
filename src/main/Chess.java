/*
 * Wes Beard - wesley.beard@mymail.champlain.edu
 * Michael Leonard - michael.leonard@mymail.chamamplain.edu
 * CSI-340 Final Project
 * 12/7/2020
 *
 * Written by Wes Beard & Michael Leonard
 *
 * This file contains the chess PApplet and all associated
 * functions such as those related to drawing and interactivity
 */

package main;

import Command.BoardReplay;
import interactivity.Popup;
import interactivity.iButton;
import pieces.*;
import processing.core.*;
import processing.sound.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.*;
import timing.GameTimer;
import static main.Constants.*;
import LoadFiles.*;
import javax.swing.*;


public class Chess extends PApplet {

    // Board settings
    final int boardSize = 1000;
    final int numSpaces = 8;
    final int squareSize = boardSize / numSpaces;

    // Piece/location variables
    ArrayList<Piece> pieces = new ArrayList<Piece>();
    ArrayList<Piece> lostPieces = new ArrayList<Piece>();
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

        // Initialize sounds
        MOVESOUND = new SoundFile(this, "sound/Move.mp3");
        MOVESOUND.amp((float) .4);
        TAKESOUND = new SoundFile(this, "sound/Take.mp3");
        TAKESOUND.amp((float) .3);
        CASTLESOUND = new SoundFile(this, "sound/Castle.mp3");
        CASTLESOUND.amp((float) .4);
        VICTORYSOUND = new SoundFile(this, "sound/Victory.mp3");
        VICTORYSOUND.amp((float) .2);
        INVALIDSOUND = new SoundFile(this, "sound/Invalid.wav");
        INVALIDSOUND.amp((float) .4);
        CHECKSOUND = new SoundFile(this, "sound/Check.wav");
        CHECKSOUND.amp((float) .1);

        // Window settings
        icon = loadImage("images/icon.png");
        surface.setIcon(icon);
        cursor(HAND);
        surface.setTitle("Chess!");
        background(LIGHT.getRGB());

        // Game setup
        Setup.setPiecePositions(pieces);
        replay = new BoardReplay(pieces, lostPieces);

        // UI settings
        textSize(36);
        textAlign(CENTER, CENTER);
        Setup.addButtons(buttons);
    }

    public void draw() {
        // Functions to be called every frame
        background(BACKGROUND.getRGB());
        handleTimer();
        drawTurnIndicator();
        if (!BoardReplay.fileReplayStarted) {
            drawLostPieces();
        }
        drawTimers();
        drawBoard();
        drawPieces();
        drawSelection();
        // If popup is active then draw popup
        if (popup) {
            drawPopup();
        }
        drawButtons();
    }

    public void keyPressed() {
        ableToSwitch = false;
        if (key == CODED) {
            // step backward
            if (keyCode == LEFT) {
                ableToSwitch = replay.stepBack(pieces, lostPieces);
                // swap turns as you go back in time
                if (ableToSwitch) {
                    selected = null;
                    MOVESOUND.play();
                    lightsTurn = !lightsTurn;
                }
            // step forward
            } else if (keyCode == RIGHT) {
                ableToSwitch = replay.stepForward(pieces, lostPieces);
                // swap turns as you go back in time
                if (ableToSwitch) {
                    selected = null;
                    MOVESOUND.play();
                    lightsTurn = !lightsTurn;
                }
            }
            // download file
            else if (keyCode == DOWN) {
                ArrayList<ArrayList<String>> boardReplay = replay.getBoardReplay();
                try {
                    String filename;
                    filename = System.currentTimeMillis() + ".chess";
                    FileWriter fileWriter = new FileWriter(filename);
                    for (ArrayList<String> board : boardReplay) {
                        for (String piece : board) {
                            fileWriter.write(piece + " ");
                        }
                        fileWriter.write("\n");
                    }
                    fileWriter.close();
                    System.out.println("Successfully downloaded the game to file " + filename);

                    popup = true;
                    showPopupButton();
                    Popup.text = "File downloaded";

                } catch (IOException e) {
                    System.out.println("An error occurred while trying to download the file.");
                    e.printStackTrace();
                }
            }
            // load file
            else if (keyCode == UP) {
                JFileChooser fc = new JFileChooser();
                // open the current directory by default
                // use user.home to open the home directory
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                int returnVal = fc.showOpenDialog(fc);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fc.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());

                    popup = true;
                    showPopupButton();
                    Popup.text = "Replay started";

                    resetBoard();

                    Context context = null;
                    // last element of splitting by .
                    // ex. michael.leonard.chess
                    //                    ^
                    String fileType = selectedFile.getAbsolutePath().split("\\.")[selectedFile.getAbsolutePath().split("\\.").length-1];
                    if(fileType.equals("fen")) {
                        context = new Context(new LoadFEN());
                    }
                    if(fileType.equals("chess")) {
                        context = new Context(new LoadCustomCHESS());
                    }
                    ArrayList<ArrayList<String>> boardReplay;
                    boardReplay = context.executeLoadStrategy(selectedFile.getAbsolutePath());
                    replay.addBoardReplay(boardReplay);
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
                handleButton(button);
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
                    if (replay.performRecordCommand(selected, targetX, targetY, pieces, toTake, lostPieces)) {
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

        // Draw board squares
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

                int fileX = 10;
                int fileY = (j * squareSize) + 100;
                int file = Util.convertFile(j);
                text(file,fileX, fileY);

                int rankX = (i * squareSize) + 100;
                int rankY = 10;
                char rank = Util.convertRank(i);
                text(rank, rankX, rankY);

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
            rect(boardSize + 20, boardSize / 2 + 30, 20, (boardSize / 2) - 50, 50);
        }
        else {
            fill(DARK.getRGB());
            rect(boardSize + 20, 30, 20, (boardSize / 2) - 50, 50);
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
        lostPieces.clear();
        Setup.setPiecePositions(pieces);
        lightsTurn = true;
        firstMoveLight = true;
        firstMoveDark = true;
        replay = new BoardReplay(pieces, lostPieces);
        System.out.println("New game started!");
    }

    public void drawLostPieces() {
        numLostDark = 0;
        numLostLight = 0;
        lightLostRows = 0;
        darkLostRows = 0;
        for (Piece piece : lostPieces) {
            piece.shape = loadShape(piece.shapeFile);
            if (piece.side == "dark") {
                numLostDark++;
                piece.x = lostDarkX + (numLostDark * 37);
                piece.y = lostDarkY + (darkLostRows * 65);

                shape(piece.shape, piece.x, piece.y, 50, 50);
                if (numLostDark % 5 == 0) {
                    darkLostRows++;
                    numLostDark = 0;
                }
            }
            else {
                numLostLight++;
                piece.x = lostLightX + (numLostLight * 37);
                piece.y = lostLightY + (lightLostRows * 65);
                shape(piece.shape, piece.x, piece.y, 50, 50);
                if (numLostLight % 5 == 0) {
                    lightLostRows++;
                    numLostLight = 0;
                }
            }
        }
    }

    public void handleTimer() {
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
        if (darkTimer.isOver) {
            darkTimer.isOver = false;
            resetBoard();
            Popup.text = "White Wins!";
        }
        if (lightTimer.isOver) {
            darkTimer.isOver = false;
            resetBoard();
            Popup.text = "Black Wins!";
        }
    }

    public void handleButton(iButton button) {

        if (button.id == "Light Resigns") {
            BoardReplay.fileReplayStarted = false;
            resetBoard();
            Popup.text = "Black Wins!";
        }
        else if (button.id == "Dark Resigns") {
            BoardReplay.fileReplayStarted = false;
            resetBoard();
            Popup.text = "White Wins!";
        }
        else if (button.id == "Draw") {
            BoardReplay.fileReplayStarted = false;
            resetBoard();
            Popup.text = "Draw!";
        }
        else if (button.id == "Ok") {
            button.hidden = true;
            popup = false;
        }
    }
}
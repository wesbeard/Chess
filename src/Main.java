import processing.core.*;

import java.awt.*;

public class Main extends PApplet {

    final int boardSize = 1000;
    final int numSpaces = 8;
    final int squareSize = boardSize/numSpaces;
    int fillColor = 0;

    Color white = new Color(255, 255, 255);
    Color black = new Color(0, 0, 0);

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        size (1200, 1000);
    }

    public void setup() {
        background(20, 30, 20);
    }

    public void draw() {
        drawBoard();
    }

    public void drawBoard() {

        for(int i = 0; i < numSpaces; i++){
            for(int j = 0; j < numSpaces;j++){

                fill(fillColor);
                rect(j * squareSize, i * squareSize, squareSize, squareSize);

                if(fillColor == black.getRGB()){
                    fillColor = white.getRGB();
                }
                else{
                    fillColor = black.getRGB();
                }
            }
            if(numSpaces %2 == 0 && fillColor == black.getRGB()){
                fillColor = white.getRGB();
            }
            else if(numSpaces %2 == 0 && fillColor == white.getRGB()){
                fillColor = black.getRGB();
            }

        }
    }
}
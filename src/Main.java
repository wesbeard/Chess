import processing.core.*;

import java.awt.*;

public class Main extends PApplet {

    final int boardSize = 1000;
    final int numSpaces = 8;
    final int squareSize = boardSize/numSpaces;
    int fillColor = 0;

    Color light = new Color(255, 255, 255);
    Color dark = new Color(50, 100, 50);

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        size (1000, 1000);
    }

    public void setup() {
        background(0, 0, 0);
    }

    public void draw() {
        drawBoard();
    }

    public void drawBoard() {

        for(int i = 0; i < numSpaces; i++){
            for(int j = 0; j < numSpaces;j++){

                fill(fillColor);
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
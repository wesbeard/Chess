package pieces;

import processing.core.*;

import java.util.ArrayList;

public abstract class Piece extends PApplet{

    public String side;
    public String type;
    public int x;
    public int y;
    public String shapeFile;
    public PShape shape;
    public String pieceSet = "tatiana";
    public boolean moved = false;

    public abstract boolean move(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake);

}

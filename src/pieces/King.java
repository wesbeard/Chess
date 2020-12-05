package pieces;

import processing.sound.SoundFile;

import java.util.ArrayList;

public class King extends Piece {

    public King (String sideColor, int x, int y) {
        super.side = sideColor;
        super.type = "K";
        super.y = y;
        super.x = x;

        if (side == "dark") {
            super.shapeFile = "images/piecesets/" + super.pieceSet + "/b" + type + ".svg";
        }
        else {
            super.shapeFile = "images/piecesets/" + super.pieceSet + "/w" + type + ".svg";
        }
    }

    @Override
    public boolean move(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake, SoundFile castleSound, SoundFile takeSound, SoundFile moveSound) {

        boolean castled = false;

        // Check for castle
        // If dark and not moved then check which side is being castled and pass ending coordinates
        if (side == "dark" && !moved) {
            if((targetX == 1 && targetY == 0) && !blockedHorizontal(1, 0, pieces, toTake)) {
                castled = castle(pieces,1, 0, 0, 0, 2, 0, castleSound);
            }
            else if((targetX == 6 && targetY == 0) && !blockedHorizontal(1, 0, pieces, toTake)) {
                castled = castle(pieces, 6, 0, 7, 0, 5, 0, castleSound);
            }
        }
        // If light and not moved then check which side is being castled and pass ending coordinates
        else if (side == "light" && !moved) {
            if((targetX == 1 && targetY == 7) && !blockedHorizontal(1, 0, pieces, toTake)) {
                castled = castle(pieces, 1, 7, 0, 7, 2, 7, castleSound);
            }
            else if((targetX == 6 && targetY == 7) && !blockedHorizontal(1, 0, pieces, toTake)) {
                castled = castle(pieces, 6, 7, 7, 7, 5, 7, castleSound);
            }
        }

        // Normal movement if not attempting castle
        if(((abs(x - targetX) <= 1) && (abs(y - targetY) <= 1)) && !castled && !isPinned(pieces, targetX, targetY, toTake)) {
            if (toTake != null) {
                takeSound.play();
            }
            else {
                moveSound.play();
            }
            super.x = targetX;
            super.y = targetY;
            isCheck(pieces, toTake);
            take(pieces,toTake);
            moved = true;
            return true;
        }

        // If castled then move is true
        if(castled) {
            return true;
        }
        // If not and others fail then move is false
        else{
            System.out.println("Invalid Move: K" + convertRank(targetX) + convertFile(targetY));
            return false;
        }
    }

    @Override
    public boolean isCheck(ArrayList<Piece> pieces, Piece toTake) {
        return false;
    }

    public boolean castle(ArrayList<Piece> pieces, int castleX, int castleY, int rookX, int rookY, int rookX2, int rookY2, SoundFile castlSound) {
        for (Piece piece : pieces) {
            if((piece.x == rookX && piece.y == rookY) && !piece.moved){
                castlSound.play();
                //Move king to castle position
                x = castleX;
                y = castleY;
                moved = true;
                //Move rook to castle position
                piece.x = rookX2;
                piece.y = rookY2;
                piece.moved = true;
                return true;
            }
        }
        return false;
    }
}

package pieces;

import processing.sound.SoundFile;

import java.util.ArrayList;

import main.Command;

import static main.Constants.*;

public class Bishop extends Piece implements Command {

    public Bishop (String sideColor, int x, int y) {
        super.side = sideColor;
        super.type = "B";
        super.y = y;
        super.x = x;

        if (side == "dark") {
            super.shapeFile = "images/piecesets/" + super.pieceSet + "/b" + type + ".svg";
        }
        else {
            super.shapeFile = "images/piecesets/" + super.pieceSet + "/w" + type + ".svg";
        }
    }

    public boolean move(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake) {
        boolean pinned = isPinned(pieces,targetX, targetY, toTake);
        if ((((targetX - x == targetY - y) || (-targetX + x == targetY - y)) && !blockedDiagonal(targetX, targetY, pieces, toTake)) && !pinned) {
            if (toTake != null) {
                TAKESOUND.play();
            }
            else {
                MOVESOUND.play();
            }
            super.x = targetX;
            super.y = targetY;
            if (isCheck(pieces, toTake)) {
                CHECKSOUND.play();
            }
            take(pieces,toTake);
            moved = true;
            return true;
        }
        System.out.println("Invalid Move B" + convertRank(targetX) + convertFile(targetY));
        INVALIDSOUND.play();
        return false;
    }

    @Override
    public boolean isCheck(ArrayList<Piece> pieces, Piece toTake) {

        Piece enemyKing = getKing(pieces, side);

        if (enemyKing != null) {
            if ((!blockedDiagonal(enemyKing.x, enemyKing.y, pieces, enemyKing)) && ((enemyKing.x - x == enemyKing.y - y) || (-enemyKing.x + x == enemyKing.y - y))) {
                System.out.println("Check by " + side + " bishop!");
                return true;
            }
        }

        return false;
    }
}

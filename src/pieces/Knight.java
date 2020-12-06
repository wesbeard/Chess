package pieces;

import processing.sound.SoundFile;

import java.util.ArrayList;
import main.Command;
import static main.Constants.*;

public class Knight extends Piece implements Command {

    public Knight (String sideColor, int x, int y) {
        super.side = sideColor;
        super.type = "N";
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
        boolean pinned = isPinned(pieces, targetX, targetY, toTake);
        int absX = abs(x - targetX);
        int absY = abs(y - targetY);

        if(((absX == 2 && absY == 1) || (absX == 1 && absY == 2)) && !pinned) {
            if (toTake != null) {
                TAKESOUND.play();
            }
            else {
                TAKESOUND.play();
            }
            super.x = targetX;
            super.y = targetY;
            if (isCheck(pieces, toTake)) {
                CHECKSOUND.play();
            }
            take(pieces, toTake);
            moved = true;
            return true;
        }
        System.out.println("Invalid Move N"  + convertRank(targetX) + convertFile(targetY));
        INVALIDSOUND.play();
        return false;
    }

    @Override
    public boolean isCheck(ArrayList<Piece> pieces, Piece toTake) {

        Piece enemyKing = getKing(pieces, side);

        if (enemyKing != null) {
            int absX = abs(x - enemyKing.x);
            int absY = abs(y - enemyKing.y);

            if ((absX == 2 && absY == 1) || (absX == 1 && absY == 2)) {
                System.out.println("Check by " + side + " Knight!");
                return true;
            }
        }
        return false;
    }
}

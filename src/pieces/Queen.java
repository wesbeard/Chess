package pieces;

import Command.Command;
import main.Util;

import java.util.ArrayList;
import static main.Constants.*;

public class Queen extends Piece implements Command {

    public Queen (String sideColor, int x, int y) {
        super.side = sideColor;
        super.type = "Q";
        super.y = y;
        super.x = x;

        if (side == "dark") {
            super.shapeFile = "images/piecesets/" + super.pieceSet + "/b" + type + ".svg";
        }
        else {
            super.shapeFile = "images/piecesets/" + super.pieceSet + "/w" + type + ".svg";
        }
    }

    public boolean move(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake, ArrayList<Piece> lostPieces) {
        boolean pinned = isPinned(pieces,targetX, targetY, toTake);
        if ((((targetX - x == targetY - y || -targetX + x == targetY - y) || (targetX == x || targetY == y)) &&
                !blockedHorizontal(targetX, targetY, pieces, toTake) && !blockedDiagonal(targetX, targetY, pieces, toTake))
                && !pinned) {
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
            take(pieces,toTake, lostPieces);
            moved = true;
            return true;
        }
        System.out.println("Invalid Move Q" + Util.convertRank(targetX) + Util.convertFile(targetY));
        INVALIDSOUND.play();
        return false;
    }

    @Override
    public boolean isCheck(ArrayList<Piece> pieces, Piece toTake) {

        Piece enemyKing = getKing(pieces, side);

        if (enemyKing != null) {
            if (((!blockedDiagonal(enemyKing.x, enemyKing.y, pieces, enemyKing)) && ((enemyKing.x - x == enemyKing.y - y) || (-enemyKing.x + x == enemyKing.y - y)))
                    || ((!blockedHorizontal(enemyKing.x, enemyKing.y, pieces, enemyKing)) && (enemyKing.x == x || enemyKing.y == y))) {

                System.out.println("Check by " + side + " Queen!");
                return true;
            }
        }

        return false;
    }
}

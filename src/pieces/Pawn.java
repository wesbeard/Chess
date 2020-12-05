package pieces;

import java.util.ArrayList;

public class Pawn extends Piece  {

    public Pawn (String sideColor, int x, int y) {
        super.side = sideColor;
        super.type = "P";
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
    public boolean move(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake){
        boolean pinned = isPinned(pieces,targetX, targetY, toTake);
        if (targetX == x && !pinned) {
            if(toTake != null){
                if(toTake.x == x){
                    return false;
                }
            }
            if (side == "light") {
                if(targetY == y - 1 || (targetY == y - 2 && !moved)) {
                    super.x = targetX;
                    super.y = targetY;
                    moved = true;
                    if(y == 0) {
                        promote(pieces);
                    }
                    return true;
                }
            }
            else {
                if(targetY == y + 1 || (targetY == y + 2 && !moved)) {
                    super.x = targetX;
                    super.y = targetY;
                    moved = true;
                    if(y == 7) {
                        promote(pieces);
                    }
                    return true;
                }
            }
        }
        else if ((targetX == x + 1 || targetX == x - 1) && toTake != null && !pinned) {
            if (side == "light") {
                if (targetY == y - 1) {
                    super.x = targetX;
                    super.y = targetY;
                    isCheck(pieces, toTake);
                    take(pieces,toTake);
                    moved = true;
                    if(y == 0) {
                        promote(pieces);
                    }
                    return true;
                }
            }
            else {
                if (targetY == y + 1) {
                    super.x = targetX;
                    super.y = targetY;
                    isCheck(pieces, toTake);
                    take(pieces,toTake);
                    moved = true;
                    if(y == 7) {
                        promote(pieces);
                    }
                    return true;
                }
            }
        }
        System.out.println("Invalid Move P" + convertRank(targetX) + convertFile(targetY));
        return false;
    }

    public void promote(ArrayList<Piece> pieces) {
        if (side == "light") {
            pieces.add(PieceFactory.getPiece("Q","light", x, y));
        }
        else {
            pieces.add(PieceFactory.getPiece("Q","dark", x, y));
        }

        pieces.remove(this);
    }

    @Override
    public boolean isCheck(ArrayList<Piece> pieces, Piece toTake) {

        Piece enemyKing = getKing(pieces, side);

        if ((enemyKing.x == x + 1 || enemyKing.x == x - 1) && toTake != null) {
            if (side == "light") {
                if (enemyKing.y == y - 1) {
                    System.out.println("Check by " + side + " Pawn!");
                    return true;
                }
            }
            else {
                if (enemyKing.y == y + 1) {
                    System.out.println("Check by " + side + " Pawn!");
                    return true;
                }
            }
        }


        return false;
    }
}

package Command;

import pieces.Piece;
import processing.sound.SoundFile;

import java.util.ArrayList;

public interface Command {

    boolean move(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake, ArrayList<Piece> lostPieces);

}

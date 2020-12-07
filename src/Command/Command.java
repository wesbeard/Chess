/*
 * Wes Beard - wesley.beard@mymail.champlain.edu
 * Michael Leonard - michael.leonard@mymail.chamamplain.edu
 * CSI-340 Final Project
 * 12/7/2020
 *
 * Written by Michael Leonard
 *
 * This file is the command interface that connects piece
 * movement to game replay
 */

package Command;

import pieces.Piece;
import processing.sound.SoundFile;

import java.util.ArrayList;

public interface Command {

    boolean move(int targetX, int targetY, ArrayList<Piece> pieces, Piece toTake, ArrayList<Piece> lostPieces);

}

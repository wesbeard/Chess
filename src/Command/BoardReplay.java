package Command;

import pieces.*;
import java.util.ArrayList;


public class BoardReplay {

    ArrayList<ArrayList<String>> allBoards;
    ArrayList<ArrayList<String>> allLostPieces;
    int currentPosition;

    public BoardReplay(ArrayList<Piece> entireBoard, ArrayList<Piece> lostPieces) {
        allBoards = new ArrayList<ArrayList<String>>();
        allLostPieces = new ArrayList<ArrayList<String>>();
        currentPosition = -1;

        // add all pieces in string format (in a string ArrayList) to the allBoards ArrayList
        ArrayList<String> currentBoard = new ArrayList<String>();
        ArrayList<String> currentLostPieces = new ArrayList<String>();
        for (Piece piece : entireBoard) {
            currentBoard.add(piece.getData());
        }
        for (Piece piece : lostPieces) {
            currentLostPieces.add(piece.getData());
        }
        allLostPieces.add(currentLostPieces);
        allBoards.add(currentBoard);

        /*for (ArrayList<String> arrList : allBoards) {
            for (String board : arrList) {
                System.out.println(board);
            }
            System.out.println(" ");
        }*/

        currentPosition += 1;
    }

    public boolean performRecordCommand(Piece pieceToMove,
                                     int targetX,
                                     int targetY,
                                     ArrayList<Piece> entireBoard,
                                     Piece toTake,
                                     ArrayList<Piece> lostPieces) {

        boolean successful = pieceToMove.move(targetX, targetY, entireBoard, toTake, lostPieces);

        if(successful) {
            // add all pieces in string format (in a string ArrayList) to the allBoards ArrayList
            ArrayList<String> currentBoard = new ArrayList<String>();
            //ArrayList<String> currentLostPieces = new ArrayList<String>();         TODO 6 lines of lost pieces should be uncommented
            for (Piece piece : entireBoard) {
                currentBoard.add(piece.getData());
            }
            //for (Piece piece : lostPieces) {
            //    currentLostPieces.add(piece.getData());
            //}
            // if someone makes a move while looking at the middle of the replay...
            // delete all instances of the board after it
            if (currentPosition != allBoards.size() - 1) {
                // I have to loop backwards because the size of allBoards changes as I delete elements
                for (int i = allBoards.size() - 1; i > currentPosition; i--) {
                    //allLostPieces.remove(i);
                    allBoards.remove(i);
                }
                System.out.println("Future boards removed");
            }
            //allLostPieces.add(currentLostPieces);
            allBoards.add(currentBoard);

            currentPosition += 1;
        }
        return successful;
    }

    // returns the new board to be shown
    public boolean stepBack(ArrayList<Piece> passedBoard, ArrayList<Piece> lostPieces) {
        boolean valid = false;

        if(currentPosition > 0) {
            currentPosition -= 1;
            valid = true;
        }

        ArrayList<Piece> newBoard = new ArrayList<Piece>();
        stringArrayToPieceArray(newBoard, allBoards.get(currentPosition));
        passedBoard.clear();
        passedBoard.addAll(newBoard);

        /*ArrayList<Piece> newLostPieces = new ArrayList<Piece>();
        stringArrayToPieceArray(newLostPieces, allLostPieces.get(currentPosition));
        lostPieces.clear();
        lostPieces.addAll(newLostPieces);*/
        return valid;
    }

    public boolean stepForward(ArrayList<Piece> passedBoard, ArrayList<Piece> lostPieces) {
        boolean valid = false;

        if(currentPosition < allBoards.size()-1) {
            currentPosition += 1;
            valid = true;
        }

        ArrayList<Piece> newBoard = new ArrayList<Piece>();
        stringArrayToPieceArray(newBoard, allBoards.get(currentPosition));
        passedBoard.clear();
        passedBoard.addAll(newBoard);

        /*ArrayList<Piece> newLostPieces = new ArrayList<Piece>();
        stringArrayToPieceArray(newLostPieces, allLostPieces.get(currentPosition));
        lostPieces.clear();
        lostPieces.addAll(newLostPieces);*/

        return valid;
    }


    // helper function
    public void stringArrayToPieceArray(ArrayList<Piece> pieceArr, ArrayList<String> stringArr) {

        // loop through all strings in board at current position
        String sideColor = null;
        for (String pieceString : stringArr) {
            if (String.valueOf(pieceString.charAt(1)).equals("L")) {
                sideColor = "light";
            } else {
                sideColor = "dark";
            }

            Piece pieceToAdd = PieceFactory.getPiece(
                    String.valueOf(pieceString.charAt(0)),
                    sideColor,
                    Character.getNumericValue(pieceString.charAt(2)),
                    Character.getNumericValue(pieceString.charAt(3))
            );
            pieceArr.add(pieceToAdd);
        }
    }

    public void addBoardReplay(ArrayList<ArrayList<String>> entireGame) {
        allBoards.clear();
        allBoards.addAll(entireGame);

        for (ArrayList<String> arrList : entireGame) {
            for (String board : arrList) {
                System.out.println(board);
            }
            System.out.println(" ");
        }

        for (ArrayList<String> arrList : allBoards) {
            for (String board : arrList) {
                System.out.println(board);
            }
            System.out.println(" ");
        }

        currentPosition = 0;
    }
}

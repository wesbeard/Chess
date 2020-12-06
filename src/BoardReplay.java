import pieces.*;

import java.util.ArrayList;

public class BoardReplay {

    ArrayList<ArrayList<String>> allBoards;
    int currentPosition;

    public BoardReplay() {
        allBoards = new ArrayList<ArrayList<String>>();
        currentPosition = -1;
    }

    public void performRecordCommand(ArrayList<Piece> entireBoard) {
        // add all pieces in string format (in a string ArrayList) to the allBoards ArrayList
        ArrayList<String> currentBoard = new ArrayList<String>();
        for (Piece piece : entireBoard) {
            currentBoard.add(piece.getData());
        }
        // if someone makes a move while looking at the middle of the replay...
        // delete all instances of the board after it
        if(currentPosition != allBoards.size()-1) {
            // I have to loop backwards because the size of allBoards changes as I delete elements
            for(int i = allBoards.size()-1; i > currentPosition; i--) {
                allBoards.remove(i);
            }
            System.out.println("Future boards have been removed");
        }
        allBoards.add(currentBoard);

        for(int i = 0; i < allBoards.size(); i++) {

        }
        currentPosition += 1;
    }

    // returns the new board to be shown
    public ArrayList<Piece> stepBack() {
        if(currentPosition > 0) {
            currentPosition -= 1;
        }

        ArrayList<Piece> newBoard = new ArrayList<Piece>();
        stringArrayToPieceArray(newBoard, allBoards.get(currentPosition));

        return newBoard;
    }

    public ArrayList<Piece> stepForward() {
        if(currentPosition < allBoards.size()-1) {
            currentPosition += 1;
        }

        ArrayList<Piece> newBoard = new ArrayList<Piece>();
        stringArrayToPieceArray(newBoard, allBoards.get(currentPosition));

        return newBoard;
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
}

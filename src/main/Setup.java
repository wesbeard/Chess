package main;

import interactivity.Popup;
import interactivity.iButton;
import pieces.*;
import java.util.ArrayList;

import static main.Constants.*;

public class Setup {

    public static void addButtons(ArrayList<iButton> buttons) {
        buttons.add(new iButton(1150, 75, 70, 180, "Resign", "Dark Resigns", DARK, false));
        buttons.add(new iButton(1150, 925, 70, 180, "Resign", "Light Resigns", LIGHT, false));
        buttons.add(new iButton(1150, 500, 70, 180, "Draw", "Draw", GREY, false));
        buttons.add(new iButton(Popup.buttonX, Popup.buttonY, Popup.buttonHeight, Popup.buttonWidth, Popup.buttonText, Popup.buttonText, DARK, true));
    }

    public static void setPiecePositions(ArrayList<Piece> pieces) {
         /*
         Create Dark Pieces
         */

        // Pawns
        for (int i = 0; i < 8; i++) {
            pieces.add(PieceFactory.getPiece("P","dark", i, 1));
        }

        // Rooks
        pieces.add(PieceFactory.getPiece("R","dark", 0, 0));
        pieces.add(PieceFactory.getPiece("R","dark", 7, 0));

        // Knights
        pieces.add(PieceFactory.getPiece("N","dark", 1, 0));
        pieces.add(PieceFactory.getPiece("N","dark", 6, 0));

        // Bishops
        pieces.add(PieceFactory.getPiece("B","dark", 2, 0));
        pieces.add(PieceFactory.getPiece("B","dark", 5, 0));

        // Queen
        pieces.add(PieceFactory.getPiece("Q","dark", 3, 0));

        // King
        pieces.add(PieceFactory.getPiece("K","dark", 4, 0));


        /*
        Create Light Pieces
        */

        // Pawns
        for (int i = 0; i < 8; i++) {
            pieces.add(PieceFactory.getPiece("P","light", i, 6));
        }

        // Rooks
        pieces.add(PieceFactory.getPiece("R","light", 0, 7));
        pieces.add(PieceFactory.getPiece("R","light", 7, 7));

        // Knights
        pieces.add(PieceFactory.getPiece("N","light", 1, 7));
        pieces.add(PieceFactory.getPiece("N","light", 6, 7));

        // Bishops
        pieces.add(PieceFactory.getPiece("B","light", 2, 7));
        pieces.add(PieceFactory.getPiece("B","light", 5, 7));

        // Queen
        pieces.add(PieceFactory.getPiece("Q","light", 3, 7));

        // King
        pieces.add(PieceFactory.getPiece("K","light", 4, 7));

    }
}

package ooga.model.game_initialization;

import ooga.model.game_components.GamePiece;

import java.util.Map;

public class BoardCreator {
    GamePiece[][] gameBoard;
    String dictionary = "abcdefghijklmnopqrstuvwxyz"; //max supported board size is 26x26

    /**
     * Builds the board based on the map of user and opponent's pieces
     *
     * @param userPieces     contains (coordinate, piece) for each of the user's starting pieces
     * @param opponentPieces contains (coordinate, piece) for each of the opponent's starting pieces
     * @param numRows        is the number of rows for the desired game board
     * @param numCols        is the number of columns for the desired game board
     */
    public void buildBoard(Map<String, String> userPieces, Map<String, String> opponentPieces, int numRows, int numCols) {
        gameBoard = new GamePiece[numRows][numCols];
        for (Map.Entry<String, String> entry : userPieces.entrySet()) {
            int[] coords = translateCoordinates(entry.getKey(), numRows);
            gameBoard[coords[0]][coords[1]] = buildPiece(entry.getValue());
        }
        for (Map.Entry<String, String> entry : opponentPieces.entrySet()) {
            int[] coords = translateCoordinates(entry.getKey(), numRows);
            gameBoard[coords[0]][coords[1]] = buildPiece(entry.getValue());
        }
    }

    /**
     * Builds a GamePiece based on the name of the piece
     *
     * @param pieceName to initialize associated xml file
     * @return GamePiece to be added to the Board
     */
    public GamePiece buildPiece(String pieceName) {
        return null;
    }

    private int[] translateCoordinates(String coordinate, int numRows) {
        int[] coords = new int[2];
        coords[0] = dictionary.indexOf(coordinate.charAt(0));
        coords[1] = -(Integer.parseInt(coordinate.substring(1)) - numRows);
        return coords;
    }

}

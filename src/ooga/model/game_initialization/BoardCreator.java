package ooga.model.game_initialization;

import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GamePiece;
import ooga.model.game_initialization.piece_initialization.PieceCreator;
import org.w3c.dom.Node;
import java.util.List;
import java.util.Map;

/**
 * This class creates a 2D GamePiece array that represents the game board,
 * adjustable by the starting state XML files which describe each occupied square in the board
 * in addition to board dimensions
 */
public class BoardCreator extends Creator {
    private static final String dictionary = "abcdefghijklmnopqrstuvwxyz"; //max supported board size is 26x26
    private static final String PATH = "src/ooga/model/game_components/data_files/starting_states/";
    private static final String FILE_TYPE = "piece";
    private GamePiece[][] gameBoard;
    private Map<String, List<Node>> boardNodes;
    private Map<String, List<Node>> subNodes;
    private Map<String, String> userPieces;
    private Map<String, String> opponentPieces;
    private int numRows;
    private int numCols;
    private final PieceCreator pieceCreator;

    public BoardCreator(String game) {
        pieceCreator = new PieceCreator(game);
        super.setComponents(PATH, FILE_TYPE, game);
        initializeMaps(game);
    }

    private void initializeMaps(String game) {
        boardNodes = super.makeRootNodeMap("chess");
        subNodes = super.makeSubNodeMap(boardNodes.get("board").get(0));
        numRows = Integer.parseInt(super.makeAttributeMap(boardNodes.get("params").get(0)).get("numrows"));
        numCols = Integer.parseInt(super.makeAttributeMap(boardNodes.get("params").get(0)).get("numcols"));
        opponentPieces = super.makeAttributeMap(subNodes.get("opponent").get(0));
        userPieces = super.makeAttributeMap(subNodes.get("user").get(0));
    }

    public GamePiece[][] makeBoard() {
        gameBoard = new GamePiece[numRows][numCols];
        for (Map.Entry<String, String> entry : userPieces.entrySet()) {
            buildPiece(numRows, entry, 1);
        }
        for (Map.Entry<String, String> entry : opponentPieces.entrySet()) {
            buildPiece(numRows, entry, -1);
        }
        return gameBoard;
    }

    public GamePiece[][] getGameBoard(){
        return gameBoard;
    }

    private void buildPiece(int numRows, Map.Entry<String, String> entry, int direction) {
        int pieceX = translateX(entry.getKey());
        int pieceY = translateY(entry.getKey(), numRows);
        Coordinate pieceCoordinate = new Coordinate(pieceX, pieceY);
        gameBoard[pieceX][pieceY] = pieceCreator.makePiece(entry.getValue(), pieceCoordinate, direction);
    }

    private int translateX(String coordinate) {
        return dictionary.indexOf(coordinate.charAt(0));
    }

    private int translateY(String coordinate, int numRows) {
        return -(Integer.parseInt(coordinate.substring(1)) - numRows);
    }
}

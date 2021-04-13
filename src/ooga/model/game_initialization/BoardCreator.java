package ooga.model.game_initialization;

import java.util.HashSet;
import java.util.Set;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_initialization.piece_initialization.PieceCreator;
import org.w3c.dom.Node;
import java.util.List;
import java.util.Map;

/**
 * This class creates a 2D GamePiece array that represents the game board, adjustable by the
 * starting state XML files which describe each occupied square in the board in addition to board
 * dimensions
 *
 * @author Shaw Phillips
 */
public class BoardCreator extends Creator {

  private static final String dictionary = "abcdefghijklmnopqrstuvwxyz"; //max supported board size is 26x26
  private static final String PATH = "data/gamelogic/starting_states/";
  private static final String FILE_TYPE = "piece";
  public static final String BOARD = "board";
  public static final String PARAMS = "params";
  public static final String NUMROWS = "numrows";
  public static final String NUMCOLS = "numcols";
  public static final String OPPONENT = "opponent";
  public static final String USER = "user";
  private String gameName;
  private FrontEndExternalAPI viewController;
  private Map<String, List<Node>> boardNodes;
  private Map<String, List<Node>> pieceSubNodes;
  private Map<String, String> userPieces;
  private Map<String, String> opponentPieces;
  private int numRows;
  private int numCols;
  private PieceCreator pieceCreator;
  private Set<GamePiece> pieceSet = new HashSet<>();
  private GameBoard board;

  public BoardCreator(String game, FrontEndExternalAPI viewController) {
    this.viewController = viewController;
    super.setComponents(PATH, FILE_TYPE, game);
    gameName = game;
    initializeMaps(game);
  }

  private void initializeMaps(String game) {
    boardNodes = super.makeRootNodeMap(game);
    pieceSubNodes = super.makeSubNodeMap(boardNodes.get(BOARD).get(0));
    numRows = Integer.parseInt(super.makeAttributeMap(boardNodes.get(PARAMS).get(0)).get(NUMROWS));
    numCols = Integer.parseInt(super.makeAttributeMap(boardNodes.get(PARAMS).get(0)).get(NUMCOLS));
    opponentPieces = super.makeAttributeMap(pieceSubNodes.get(OPPONENT).get(0));
    userPieces = super.makeAttributeMap(pieceSubNodes.get(USER).get(0));
  }

  public GameBoard makeBoard() {
    board = new GameBoard(numCols, numRows);
    viewController.setBoardDimensions(numCols, numRows);
    pieceCreator = new PieceCreator(gameName, viewController, board);

    for (Map.Entry<String, String> entry : userPieces.entrySet()) {
      buildPiece(numRows, entry, -1, USER);
    }
    for (Map.Entry<String, String> entry : opponentPieces.entrySet()) {
      buildPiece(numRows, entry, 1, OPPONENT);
    }
    return board;
  }

  private void buildPiece(int numRows, Map.Entry<String, String> entry, int direction,
      String team) {
    int pieceX = translateX(entry.getKey());
    int pieceY = translateY(entry.getKey(), numRows);
    String pieceType = entry.getValue();
    Coordinate pieceCoordinate = new Coordinate(pieceX, pieceY);
    GamePiece newPiece = pieceCreator
        .makePiece(pieceType, pieceCoordinate, direction, viewController);
    pieceSet.add(newPiece);
    newPiece.setPieceTeam(team);
    board.addPiece(newPiece);
    viewController.setBoardSpace(pieceX, pieceY, pieceType, team);
  }

  private int translateX(String coordinate) {
    return dictionary.indexOf(coordinate.charAt(0));
  }

  private int translateY(String coordinate, int numRows) {
    return -(Integer.parseInt(coordinate.substring(1)) - numRows);
  }
}

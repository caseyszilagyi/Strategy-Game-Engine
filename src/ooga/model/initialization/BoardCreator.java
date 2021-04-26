package ooga.model.initialization;

import java.util.HashSet;
import java.util.Set;
import ooga.controller.FrontEndExternalAPI;
import ooga.exceptions.XMLParseException;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.initialization.pieces.PieceCreator;
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

  private static final String DICTIONARY = "abcdefghijklmnopqrstuvwxyz"; //max supported board size is 26x26
  private static final String PATH = "data/gamelogic/starting_states/";
  // Tags for parsing
  public static final String BOARD = "board";
  public static final String PARAMS = "params";
  public static final String NUMROWS = "numrows";
  public static final String NUMCOLS = "numcols";
  public static final String OPPONENT = "opponent";
  public static final String USER = "user";
  private static final String FILE_TYPE = "piece";

  // Maps and sets used for storage of information
  private Map<String, List<Node>> boardNodes;
  private Map<String, List<Node>> pieceSubNodes;
  private Map<String, String> userPieces;
  private Map<String, String> opponentPieces;
  private Set<GamePiece> userPieceSet = new HashSet<>();
  private Set<GamePiece> opponentPieceSet = new HashSet<>();


  private FrontEndExternalAPI viewController;
  private String gameName;
  private int numRows;
  private int numCols;
  private PieceCreator pieceCreator;
  private GameBoard board;

  public BoardCreator(String game, FrontEndExternalAPI viewController) {
    this.viewController = viewController;
    super.setComponents(PATH, FILE_TYPE, game);
    gameName = game;
    initializeMaps(gameName);
  }

  public void initializeMaps(String fileName) {
    try {
      boardNodes = super.makeRootNodeMap(fileName);
      pieceSubNodes = super.makeSubNodeMap(boardNodes.get(BOARD).get(0));
      numRows = Integer
          .parseInt(super.makeAttributeMap(boardNodes.get(PARAMS).get(0)).get(NUMROWS));
      numCols = Integer
          .parseInt(super.makeAttributeMap(boardNodes.get(PARAMS).get(0)).get(NUMCOLS));
      opponentPieces = super.makeAttributeMap(pieceSubNodes.get(OPPONENT).get(0));
      userPieces = super.makeAttributeMap(pieceSubNodes.get(USER).get(0));
    }
    catch (NullPointerException e){
      throw new XMLParseException("InvalidBoardFile");
    } catch(NumberFormatException e){
      throw new XMLParseException("MissingRowColumnTag");
    }
  }

  public GameBoard makeBoard() {
    board = new GameBoard(numCols, numRows);
    board.setViewController(viewController);
    viewController.setBoardDimensions(numCols, numRows);
    pieceCreator = new PieceCreator(gameName, board);

    for (Map.Entry<String, String> entry : userPieces.entrySet()) {
      buildPiece(numRows, entry, -1, USER, userPieceSet);
    }
    for (Map.Entry<String, String> entry : opponentPieces.entrySet()) {
      buildPiece(numRows, entry, 1, OPPONENT, opponentPieceSet);
    }
    board.nextTurn();
    return board;
  }

  private void buildPiece(int numRows, Map.Entry<String, String> entry, int direction,
      String team, Set<GamePiece> setToAdd) {
    int pieceX = translateX(entry.getKey());
    int pieceY = translateY(entry.getKey(), numRows);
    String pieceType = entry.getValue();
    Coordinate pieceCoordinate = new Coordinate(pieceX, pieceY);
    GamePiece newPiece = pieceCreator
        .makePiece(pieceType, pieceCoordinate, direction, team);
    setToAdd.add(newPiece);
    board.addPiece(newPiece);
  }


  public void setTeams(String userTeamName, String opponentTeamName){
    for(GamePiece piece: userPieceSet){
      piece.setPieceTeam(userTeamName);
    }
    for(GamePiece piece: opponentPieceSet){
      piece.setPieceTeam(opponentTeamName);
    }
  }

  private int translateX(String coordinate) {
    return DICTIONARY.indexOf(coordinate.charAt(0));
  }

  private int translateY(String coordinate, int numRows) {
    return -(Integer.parseInt(coordinate.substring(1)) - numRows);
  }
}

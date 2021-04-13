package ooga.model.initialization.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.components.moves.PieceMovement;
import ooga.model.initialization.Creator;
import ooga.model.initialization.fileparsing.XMLParser;
import org.w3c.dom.Node;

/**
 * This class is used to coordinate the creation of pieces, starting from parsing through
 * the XML file and then going on to use reflection to compose the pieces
 *
 * @author Casey Szilagyi
 */
public class PieceCreator extends Creator {

  private final String PIECE_FILE_PATH = "data/gamelogic/pieces/";
  private final String FILE_TYPE = "piece";
  private String gameType;

  private XMLParser xmlParser = new XMLParser();
  private PieceComponentClassLoader pieceComponentClassLoader;

  // Intermediate node maps to keep track of things
  private Map<String, List<Node>> pieceFileNodes;
  private Map<String, List<Node>> pieceMoves;
  private final String PIECE_MOVE_TAG = "moves";

  private FrontEndExternalAPI viewController;
  private GameBoard gameBoard;


  /**
   * Initializes this piece creator which will be used to make pieces for the given game
   * @param gameType The type of game that will be played
   */
  public PieceCreator (String gameType, FrontEndExternalAPI viewController, GameBoard gameBoard){
    this.gameType = gameType;
    super.setComponents(PIECE_FILE_PATH, FILE_TYPE, this.gameType);
    this.viewController = viewController;
    this.gameBoard = gameBoard;
    pieceComponentClassLoader = new PieceComponentClassLoader(gameBoard, viewController);
  }

  /**
   * Makes a GamePiece object
   * @param pieceName The name of the piece (corresponds to the name of a file)
   * @param coordinates The coordinates that the piece is at
   * @param direction The direction that the piece can move, relative to the data file information
   * @param viewController The view controller, used to pass movement information to the front end
   * @return The GamePiece that has been instantiated.
   */
  public GamePiece makePiece(String pieceName, Coordinate coordinates, int direction, FrontEndExternalAPI viewController){
    GamePiece gamePiece = new GamePiece(coordinates, pieceName, viewController, gameBoard);
    pieceFileNodes = super.makeRootNodeMap(pieceName);
    gamePiece.setPossibleMoves(makePieceMovements(direction));
    return gamePiece;
  }

  private List<PieceMovement> makePieceMovements(int direction){
    List<PieceMovement> pieceMovements = new ArrayList<>();
    pieceMoves = super.makeSubNodeMap(getFirstNode(pieceFileNodes, PIECE_MOVE_TAG));
    for(String s: pieceMoves.keySet()){
      for(Node n: pieceMoves.get(s)){
        pieceMovements.add(pieceComponentClassLoader.makePieceMove(s, makeAttributeMap(n), direction));
      }
    }
    return pieceMovements;
  }





}
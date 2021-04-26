package ooga.model.initialization.pieces;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.exceptions.XMLParseException;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.components.moveconditions.Condition;
import ooga.model.components.moverestrictions.Restriction;
import ooga.model.components.moves.PieceMovement;
import ooga.model.initialization.Creator;
import org.w3c.dom.Node;

/**
 * This class is used to coordinate the creation of pieces, starting from parsing through the XML
 * file and then going on to use reflection to compose the pieces
 *
 * @author Casey Szilagyi
 */
public class PieceCreator extends Creator {

  private final String FILE_TYPE = "piece";
  private final String PIECE_FILE_PATH = "data/gamelogic/pieces/";
  private final String PIECE_MOVE_TAG = "moves";
  private final String RESTRICTION_TAG = "restrictions";
  private final String CONDITION_TAG = "conditions";
  private String gameType;

  private PieceComponentClassLoader pieceComponentClassLoader;

  // Intermediate node maps to keep track of things
  private Map<String, List<Node>> pieceFileNodes;
  private Map<String, List<Node>> pieceMoves;
  private Map<String, List<Node>> moveSubNodeMap;
  private Map<String, List<Node>> specificMoveComponent;

  /**
   * Initializes this piece creator which will be used to make pieces for the given game
   *
   * @param gameType The type of game that will be played
   */
  public PieceCreator(String gameType, GameBoard gameBoard) {
    this.gameType = gameType;
    super.setComponents(PIECE_FILE_PATH, FILE_TYPE, this.gameType);
    pieceComponentClassLoader = new PieceComponentClassLoader(gameBoard);
  }

  /**
   * Makes a GamePiece object
   *
   * @param pieceName      The name of the piece (corresponds to the name of a file)
   * @param coordinates    The coordinates that the piece is at
   * @param direction      The direction that the piece can move, relative to the data file
   *                       information
   * @return The GamePiece that has been instantiated.
   */
  public GamePiece makePiece(String pieceName, Coordinate coordinates, int direction, String team) {
    GamePiece gamePiece = new GamePiece(coordinates, pieceName, team);
    try{
      pieceFileNodes = super.makeRootNodeMap(pieceName);
    } catch(XMLParseException e){
      throw new XMLParseException("NoSuchPieceFile");
    }
    gamePiece.setPossibleMoves(makePieceMovements(direction, gamePiece));
    return gamePiece;
  }

  private List<PieceMovement> makePieceMovements(int direction, GamePiece correspondingPiece) {
    List<PieceMovement> pieceMovements = new ArrayList<>();
    try{
      pieceMoves = super.makeSubNodeMap(getFirstNode(pieceFileNodes, PIECE_MOVE_TAG));
    } catch(NullPointerException e){
      throw new XMLParseException("NoMovesTag");
    }

    for (String moveName : pieceMoves.keySet()) {
      for (Node moveDetails : pieceMoves.get(moveName)) {
        moveSubNodeMap = makeSubNodeMap(moveDetails);
        PieceMovement currentMovement = pieceComponentClassLoader
            .makePieceMove(moveName, makeAttributeMap(moveDetails), direction, correspondingPiece);
        currentMovement.setRestrictions(addRestrictions(moveDetails, correspondingPiece));
        currentMovement.setConditions(addConditions(moveDetails, correspondingPiece, direction));
        pieceMovements.add(currentMovement);
      }
    }
    return pieceMovements;
  }


  // could refactor this to use generic types & lambdas, just pass the specific class loader
  // call and return a generic list? maybe?

  private List<Condition> addConditions(Node moveDetails, GamePiece correspondingPiece, int direction){
    List<Condition> currentConditions = new ArrayList<>();
    if (moveSubNodeMap.containsKey(CONDITION_TAG)) {
      specificMoveComponent = makeSubNodeMap(moveSubNodeMap.get(CONDITION_TAG).get(0));
      currentConditions = makeConditions(correspondingPiece,direction);
    }
    return currentConditions;
  }

  private List<Condition> makeConditions(GamePiece correspondingPiece, int direction) {
    List<Condition> currentConditions = new ArrayList<>();
    for (String conditionName : specificMoveComponent.keySet()) {
      Map<String, String> parameters = new HashMap<>();
      //Only get parameter map if there are any
      if(getFirstNode(specificMoveComponent, conditionName).getFirstChild()!= null){
        parameters = makeAttributeMap(getFirstNode(specificMoveComponent, conditionName));
      }
      Condition currentCondition = pieceComponentClassLoader.makeCondition(conditionName,
          parameters, correspondingPiece, direction);
      currentConditions.add(currentCondition);
    }
    return currentConditions;
  }


  private List<Restriction> addRestrictions(Node moveDetails, GamePiece correspondingPiece) {
    List<Restriction> currentRestrictions = new ArrayList<>();
    if (moveSubNodeMap.containsKey(RESTRICTION_TAG)) {
      specificMoveComponent = makeSubNodeMap(moveSubNodeMap.get(RESTRICTION_TAG).get(0));
      currentRestrictions = makeRestrictions(correspondingPiece);
    }
    return currentRestrictions;
  }

  private List<Restriction> makeRestrictions(GamePiece correspondingPiece) {
    List<Restriction> currentRestrictions = new ArrayList<>();
    for (String restrictionName : specificMoveComponent.keySet()) {
      Map<String, String> parameters = new HashMap<>();
      //Only get parameter map if there are any
      if(getFirstNode(specificMoveComponent, restrictionName).getFirstChild()!= null){
        parameters = makeAttributeMap(getFirstNode(specificMoveComponent, restrictionName));
      }
      Restriction currentRestriction = pieceComponentClassLoader.makeRestriction(restrictionName,
          parameters, correspondingPiece);
      currentRestrictions.add(currentRestriction);
    }
    return currentRestrictions;
  }


}

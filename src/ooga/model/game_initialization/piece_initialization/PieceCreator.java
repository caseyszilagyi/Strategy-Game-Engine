package ooga.model.game_initialization.piece_initialization;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GamePiece;
import ooga.model.game_components.move_types.PieceMovement;
import ooga.model.game_initialization.Creator;
import ooga.model.game_initialization.file_parsing.XMLParser;
import org.w3c.dom.Node;

/**
 * This class is used to coordinate the creation of pieces, starting from parsing through
 * the XML file and then going on to use reflection to compose the pieces
 *
 * @author Casey Szilagyi
 */
public class PieceCreator extends Creator {

  private final String PIECE_FILE_PATH = "src/ooga/model/game_components/data_files/pieces/";
  private final String FILE_TYPE = "piece";
  private final String GAME_TYPE;

  private XMLParser xmlParser = new XMLParser();
  private PieceComponentClassLoader pieceComponentClassLoader = new PieceComponentClassLoader();

  // Intermediate node maps to keep track of things
  private Map<String, List<Node>> pieceFileNodes;
  private Map<String, List<Node>> pieceMoves;
  private final String PIECE_MOVE_TAG = "moves";


  /**
   * Initializes this piece creator which will be used to make pieces for the given game
   * @param gameType The type of game that will be played
   */
  public PieceCreator (String gameType){
    GAME_TYPE = gameType;
    super.setComponents(PIECE_FILE_PATH, FILE_TYPE, GAME_TYPE);
  }


  public GamePiece makePiece(String pieceName, Coordinate coordinates){
    GamePiece gamePiece = new GamePiece(coordinates);
    pieceFileNodes = super.makeRootNodeMap(pieceName);
    gamePiece.setPossibleMoves(makePieceMovements());
    return gamePiece;
  }

  private List<PieceMovement> makePieceMovements(){
    List<PieceMovement> pieceMovements = new ArrayList<>();
    pieceMoves = super.makeSubNodeMap(getFirstNode(pieceFileNodes, PIECE_MOVE_TAG));
    for(String s: pieceMoves.keySet()){
      for(Node n: pieceMoves.get(s)){
        pieceMovements.add(pieceComponentClassLoader.makePieceMove(s, makeAttributeMap(n)));
      }
    }
    return pieceMovements;
  }



}

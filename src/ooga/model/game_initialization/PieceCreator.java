package ooga.model.game_initialization;

import java.io.File;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import ooga.model.game_initialization.file_parsing.XMLParser;
import org.w3c.dom.Node;

/**
 * This class is used to coordinate the creation of pieces, starting from parsing through
 * the XML file and then going on to use reflection to compose the pieces
 *
 * @author Casey Szilagyi
 */
public class PieceCreator extends Creator{

  private final String PIECE_FILE_PATH = "src/ooga/model/game_components/data_files/pieces/";
  private final String FILE_TYPE = "piece";
  private final String GAME_TYPE;

  private XMLParser xmlParser = new XMLParser();
  private Map<String, List<Node>> rootElementNode;


  /**
   * Initializes this piece creator which will be used to make pieces for the given game
   * @param gameType The type of game that will be played
   */
  public PieceCreator (String gameType){
    GAME_TYPE = gameType;
    super.setComponents(PIECE_FILE_PATH, FILE_TYPE, GAME_TYPE);
  }


  public void makePiece(String pieceName){
    rootElementNode = super.makeRootNodeMap(pieceName);
  }


}

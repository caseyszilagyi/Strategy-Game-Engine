package ooga.model.game_initialization;

import java.io.File;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import ooga.model.game_initialization.file_parsing.XMLParser;
import org.w3c.dom.Node;

/**
 * Meant to be extended by creators to make different components of the game. So boards,
 * pieces, players, etc. The creator classes are meant to serve as an interface between
 * the XML parser and getting the actual object that is desired
 *
 * @author Casey Szilagyi
 */
public abstract class Creator {

  private final String FILE_EXTENSION = ".xml";
  private String filePath;
  private String fileType;
  private String gameType;

  private final XMLParser xmlParser = new XMLParser();

  /**
   * Gets the necessary information in order to be able to parse through the XML files
   * @param filePath The file path that leads to the directory where the given object
   *                 is located
   * @param fileType The type of game component that is being made (piece, board, etc)
   * @param gameType The name of the game that the piece belongs to
   */
  public void setComponents(String filePath, String fileType, String gameType){
    this.filePath = filePath;
    this.fileType = fileType;
    this.gameType = gameType;
  }



  /**
   * Makes a map of the names of children of the given node to the text values inside of them
   * @param node The node
   * @return The map
   */
  protected Map<String, List<String>> makeAttributeMap(Node node){
    return xmlParser.makeAttributeMap(node);
  }

  /**
   * Makes a map of the names of children of a node to the node objects themselves
   * @param node The node
   * @return The map of the names of the node's children and the nodes that they represent
   */
  protected Map<String, List<Node>> makeSubNodeMap(Node node){
    return xmlParser.makeNodeMap(node);
  }

  /**
   * Makes the root node map, given a name of the file
   * @param fileName The name of the file.
   */
  protected Map<String, List<Node>> makeRootNodeMap(String fileName){
    return xmlParser.makeRootNodeMap(makeFile(fileName), fileType, gameType);
  }

  // Makes a file, using the path and the name of the file (without the .xml)
  private File makeFile(String fileName){
    return new File(filePath + fileName + FILE_EXTENSION);
  }

}

package ooga.model.game_initialization.file_parsing;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import ooga.ExceptionHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * This class handles parsing XML files
 *
 * @author Casey Szilagyi
 * @author Robert C. Duvall
 */
public class XMLParser {

  // keep only one documentBuilder because it is expensive to make and can reset it before parsing
  private final DocumentBuilder DOCUMENT_BUILDER;

  // fileType is the type of file that is expected (piece, board), gameName is the name of the game
  // (chess, checkers)
  private String fileType;
  private String gameName;

  /**
   * Create parser for XML files of given type.
   */
  public XMLParser() throws ExceptionHandler {
    DOCUMENT_BUILDER = getDocumentBuilder();
  }


  /**
   * Given a file, makes the map that has the name of each direct child of the document element and
   * the node objects that represent them. Does some error checking
   *
   * @param dataFile The file to read in
   * @param fileType The type of file that is expected (piece, board, etc)
   * @param gameName The name of the game that the file corresponds to
   * @return The map for the child nodes of the document element
   * @throws ExceptionHandler If the file type and game name aren't correct
   */
  public Map<String, Node> makeRootNodeMap(File dataFile, String fileType, String gameName)
      throws ExceptionHandler {
    this.fileType = fileType;
    this.gameName = gameName;
    Element root = getRootElement(dataFile);
    //Checks if file is of correct type
    if (!isValidFile(root)) {
      throw new ExceptionHandler("InvalidFileType");
    }
    return makeNodeMap(root);
  }

  /**
   * Given a node, makes a map that has the name of each direct child node and the node objects that
   * represent these names
   *
   * @param parentNode The parent node that has children to be parsed through
   */
  public Map<String, Node> makeNodeMap(Node parentNode) {
    Map<String, Node> results = new HashMap<>();
    Node childNode = parentNode.getFirstChild();
    while (childNode != null) {
      results.put(childNode.getNodeName(), childNode);
      childNode = childNode.getNextSibling();
    }
    return results;
  }

  /**
   * Given a node, will make a map of the names of the children of the nodes to the text values that
   * they hold. This method assumes that each node will only have text as the child, and not other
   * nodes. It will still execute otherwise, but the map will likely not contain the desired data
   *
   * @param node The node that will be used to make the map
   * @return The map with the children of the nodes mapped to their text values
   */
  public Map<String, String> makeAttributeMap(Node node) {
    Map<String, Node> nodeMap = makeNodeMap(node);
    Map<String, String> result = new HashMap<String, String>();
    for (String key : nodeMap.keySet()) {
      result.put(key, nodeMap.get(key).getTextContent());
    }
    return result;
  }


  // get root element of an XML file
  private Element getRootElement(File xmlFile) throws ExceptionHandler {
    try {
      DOCUMENT_BUILDER.reset();
      Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    } catch (SAXException | IOException e) {
      e.printStackTrace();
      throw new ExceptionHandler("NoRootElement");
    }
  }

  // returns if this is a valid XML file for the specified object type
  private boolean isValidFile(Element root) {
    return getAttribute(root, fileType).equals(gameName);
  }

  // get value of Element's attribute
  private String getAttribute(Element e, String attributeName) {
    return e.getAttribute(attributeName);
  }

  // get value of Element's text
  private String getTextValue(Element e, String tagName) {
    NodeList nodeList = e.getElementsByTagName(tagName);
    return nodeList.item(0).getTextContent();
  }

  // boilerplate code needed to make a documentBuilder
  private DocumentBuilder getDocumentBuilder() throws ExceptionHandler {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new ExceptionHandler("DocumentBuilderFailure");
    }
  }
}


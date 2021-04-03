package ooga.model.game_initialization.file_parsing;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import ooga.ErrorHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * This class handles parsing XML files and returning a completed object.
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 */
public class XMLParser {
  // Readable error message that can be displayed by the GUI
  public static final String ERROR_MESSAGE = "XML file does not represent %s";
  // name of root attribute that notes the type of file expecting to parse
  private final String FILE_TYPE;
  private final String GAME_NAME;
  // keep only one documentBuilder because it is expensive to make and can reset it before parsing
  private final DocumentBuilder DOCUMENT_BUILDER;


  /**
   * Create parser for XML files of given type.
   */
  public XMLParser (String fileType, String gameName) {
    DOCUMENT_BUILDER = getDocumentBuilder();
    FILE_TYPE = fileType;
    GAME_NAME = gameName;
  }


  /**
   * Get data contained in this XML file as an object
   */
  public Map<String, Node> getElementMap(File dataFile) {
    Element root = getRootElement(dataFile);
    if (!isValidFile(root)) {
      throw new ErrorHandler("InvalidFileType");
    }
    Map<String, Node> results = new HashMap<>();
    NodeList nodes = root.getChildNodes();
    for (int i = 0; i < nodes.getLength(); i++) {
      results.put(nodes.item(i).getNodeName(), nodes.item(i));
    }
    return results;
  }


  /**
   * Get data contained in this XML file as an object
   */
  public Map<String, String> getAttribute(File dataFile) {
    Element root = getRootElement(dataFile);
    if (!isValidFile(root)) {
      throw new ErrorHandler("InvalidFileType");
    }
    Map<String, String> results = new HashMap<>();
    NodeList nodes = root.getChildNodes();
    for (int i = 0; i < nodes.getLength(); i++) {
      results.put(nodes.item(i).getNodeName(), nodes.item(i).getTextContent());
    }
    return results;
  }

  // get root element of an XML file
  private Element getRootElement (File xmlFile){
    try {
      DOCUMENT_BUILDER.reset();
      Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    }
    catch (SAXException | IOException e) {
      e.printStackTrace();
      throw new ErrorHandler("NoRootElement");
    }
  }

  // returns if this is a valid XML file for the specified object type
  private boolean isValidFile (Element root) {
    return getAttribute(root, FILE_TYPE).equals(GAME_NAME);
  }

  // get value of Element's attribute
  private String getAttribute (Element e, String attributeName) {
    return e.getAttribute(attributeName);
  }

  // get value of Element's text
  private String getTextValue (Element e, String tagName) {
    NodeList nodeList = e.getElementsByTagName(tagName);
    return nodeList.item(0).getTextContent();
  }

  // boilerplate code needed to make a documentBuilder
  private DocumentBuilder getDocumentBuilder () {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }
    catch (ParserConfigurationException e) {
      throw new ErrorHandler("DocumentBuilderFailure");
    }
  }
}

